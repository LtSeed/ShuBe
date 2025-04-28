package org.example.shubackend.service.device;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyCode;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;
import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.example.shubackend.entity.work.device.event.EmergencyTriggeredEvent;
import org.example.shubackend.repository.DeviceEventLogRepository;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.example.shubackend.repository.EmergencyRepository;
import org.example.shubackend.service.area.AreaEmergencyService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * – Maintains a ring buffer of recent DeviceEventFired
 * – Checks emergency trigger conditions
 * – Writes DeviceEventLog / EmergencyLog once per ON cycle
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceEventListener {

    private static final int RING_SECONDS = 120;
    private final EmergencyRepository emergencyRepo;
    private final ConditionEvaluator evaluator;
    private final DeviceEventLogRepository eventLogRepo;
    private final EmergencyLogRepository emLogRepo;
    private final org.springframework.context.ApplicationEventPublisher publisher;
    /* ===== in-memory state ===== */
    private final Deque<DeviceEventFired> ring = new ArrayDeque<>();
    /**
     * deviceId -> active event ids
     */
    private final Map<Integer, Set<Integer>> activeEvents = new ConcurrentHashMap<>();
    /**
     * deviceId -> active emergency ids
     */
    private final Map<Integer, Set<Integer>> activeEmergencies = new ConcurrentHashMap<>();
    private final AreaEmergencyService areaEmergencyService;

    /* === 1) receive DeviceEventFired from TelemetryProcessor === */
    @EventListener
    @Transactional
    public void onDeviceEvent(DeviceEventFired fired) {

        /* log ON edge */
        Set<Integer> set = activeEvents.computeIfAbsent(
                fired.device().getId(), k -> new HashSet<>());

        if (set.add(fired.meta().getId())) {               // true = new ON
            eventLogRepo.save(new DeviceEventLog(
                    null, fired.device(), fired.meta(), fired.timestamp()));
            log.info("📄 logged DeviceEvent {} for device {}", fired.meta().getCode(),
                    fired.device().getId());
        }

        /* keep recent events in ring buffer */
        ring.addLast(fired);
        trimOld(fired.timestamp());

        /* evaluate emergencies */
        for (Emergency em : emergencyRepo.findAll()) {
            if (matchEmergency(em, fired.timestamp())) {
                triggerEmergencyOnce(fired.device(), em, fired.timestamp());
            }
        }

    }

    /* === 2) helper: fire EmergencyTriggered + log ON edge === */
    private void triggerEmergencyOnce(Device device, Emergency em, Instant ts) {
        Set<Integer> emSet = activeEmergencies.computeIfAbsent(
                device.getId(), k -> new HashSet<>());

        if (emSet.add(em.getId())) {       // first time ON
            emLogRepo.save(new EmergencyLog(null, device, em, ts));
            log.warn("🚨 Emergency {} triggered on device {}", em.getName(), device.getId());
            publisher.publishEvent(new EmergencyTriggeredEvent(em, device, ts));
        }
        EmergencyCode type = em.getCode();
        areaEmergencyService.checkAreaEmergency(device, type);
    }

    /* === 3) emergency matching against ring buffer === */
    private boolean matchEmergency(Emergency em, Instant now) {
        var cond = em.getTriggerCondition();
        return evaluator.emergencyMatches(cond, ring, now);
    }

    private void trimOld(Instant now) {
        while (!ring.isEmpty() &&
                ring.peekFirst().timestamp().isBefore(now.minusSeconds(RING_SECONDS))) {
            ring.pollFirst();
        }
        // also clear OFF-state events / emergencies (simple version)
        cleanInactive(now.minusSeconds(RING_SECONDS));
    }

    /**
     * remove event/emergency ids whose last occurrence is too old
     */
    private void cleanInactive(Instant threshold) {

        // events
        Map<Integer, Set<Integer>> stillActive =
                ring.stream()
                        .filter(e -> !e.timestamp().isBefore(threshold))
                        .collect(Collectors.groupingBy(
                                e -> e.device().getId(),
                                Collectors.mapping(e -> e.meta().getId(), Collectors.toSet())));

        activeEvents.keySet().forEach(did -> {
            activeEvents.put(did, stillActive.getOrDefault(did, Collections.emptySet()));
        });

        // emergencies – when not re-triggered in window, mark OFF
        activeEmergencies.keySet().forEach(did -> {
            Set<Integer> act = activeEmergencies.get(did);
            act.removeIf(eid ->   // remove if not seen in window
                    ring.stream().noneMatch(ev ->
                            ev.device().getId().equals(did) &&
                                    ev.meta().getEmergency() != null &&
                                    ev.meta().getEmergency().getId().equals(eid) &&
                                    !ev.timestamp().isBefore(threshold)));
        });
    }
}
