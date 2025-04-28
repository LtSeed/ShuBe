package org.example.shubackend.service.area;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.work.device.emergency.AreaEmergencyLog;
import org.example.shubackend.entity.work.device.event.AreaEmergencyTriggeredEvent;
import org.example.shubackend.repository.AreaEmergencyLogRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AreaEmergencyPersister {

    private final AreaEmergencyLogRepository repo;

    @EventListener
    @Transactional
    public void onAreaEvent(AreaEmergencyTriggeredEvent ev) {
        repo.save(AreaEmergencyLog.builder()
                .areaLocation(ev.areaLocation())
                .code(ev.code())
                .timestamp(ev.timestamp())
                .build());
        log.warn("✅ AreaEmergencyLog saved for area {} type {}", ev.areaLocation(), ev.code());
    }
}
