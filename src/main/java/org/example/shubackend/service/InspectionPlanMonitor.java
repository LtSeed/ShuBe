package org.example.shubackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.entity.work.device.event.EmergencyTriggered;
import org.example.shubackend.entity.work.inspection.InspectionPlan;
import org.example.shubackend.entity.work.inspection.InspectionRecord;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.example.shubackend.repository.EmergencyRepository;
import org.example.shubackend.repository.InspectionPlanRepository;
import org.example.shubackend.repository.InspectionRecordRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

import static org.example.shubackend.entity.work.device.emergency.EmergencyCode.INSPECTION_MISSED;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class InspectionPlanMonitor {

    /**
     * 中文频率 → 周期毫秒数
     */
    private static final Map<String, Long> freqToMillis = Map.of(
            "每日", 86_400_000L,
            "每周", 604_800_000L,
            "每月", 2_592_000_000L,  // 30d
            "每季度", 7_776_000_000L   // 90d
    );
    private final InspectionPlanRepository planRepo;
    private final InspectionRecordRepository recordRepo;
    private final EmergencyRepository emRepo;
    private final EmergencyLogRepository emLogRepo;
    private final ApplicationEventPublisher publisher;

    /**
     * 每 10 分钟跑一次即可
     */
    @Scheduled(fixedRate = 10 * 60 * 1000, initialDelay = 30_000)
    @Transactional
    public void checkPlans() {

        Emergency missEm = emRepo.findByCode(INSPECTION_MISSED)
                .orElseThrow(() -> new IllegalStateException("Emergency INSPECTION_MISSED not configured"));

        Instant now = Instant.now();

        for (InspectionPlan plan : planRepo.findAll()) {
            long period = freqToMillis.getOrDefault(plan.getFrequency(), 0L);
            if (period == 0) continue;  // 未识别频率，忽略

            /* 最近一次记录 */
            Instant lastDone = recordRepo
                    .findTopByPlanOrderByInspectionTimeDesc(plan)
                    .map(InspectionRecord::getInspectionTime)
                    .orElse(Instant.EPOCH);

            boolean overdue = now.toEpochMilli() - lastDone.toEpochMilli() > period;

            if (overdue && !alreadyLogged(plan, missEm)) {
                log.warn("⏰ InspectionPlan {} overdue, triggering emergency", plan.getId());
                EmergencyLog logEntry = new EmergencyLog(null, plan.getDevice(), missEm, now);
                emLogRepo.save(logEntry);
                publisher.publishEvent(new EmergencyTriggered(missEm, plan.getDevice(), now));
            }
        }
    }

    /**
     * 防止同一计划在同一期内重复写 log
     */
    private boolean alreadyLogged(InspectionPlan plan, Emergency em) {
        return emLogRepo.existsByDeviceAndEmergencyAndTimestampAfter(
                plan.getDevice(), em,
                Instant.now().minusMillis(freqToMillis.getOrDefault(plan.getFrequency(), 0L))
        );
    }
}
