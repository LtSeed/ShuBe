package org.example.shubackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.example.shubackend.entity.work.device.event.EmergencyTriggeredEvent;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.example.shubackend.repository.EmergencyRepository;
import org.example.shubackend.repository.RepairOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.example.shubackend.entity.work.device.emergency.EmergencyCode.REPAIR_OVERDUE;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class RepairOrderMonitor {

    private final RepairOrderRepository orderRepo;
    private final EmergencyRepository emRepo;
    private final EmergencyLogRepository emLogRepo;
    private final ApplicationEventPublisher publisher;

    /**
     * deviceId -> active overdue emergency ids (防止重复)
     */
    private final Set<Integer> active = ConcurrentHashMap.newKeySet();

    /**
     * 每 5 分钟扫描一次
     */
    @Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 45_000)
    @Transactional
    public void checkOrders() {

        Emergency overdueEm = emRepo.findByCode(REPAIR_OVERDUE)
                .orElseThrow(() -> new IllegalStateException("Emergency REPAIR_OVERDUE 未配置"));

        Instant now = Instant.now();

        orderRepo.findAll().forEach(order -> {

            boolean finished = "已完成".equals(order.getStatus()) || "已关闭".equals(order.getStatus());
            boolean overdue = order.getDue() != null && now.isAfter(order.getDue());

            int key = order.getId();               // 以工单维度去重

            if (overdue && !finished && active.add(key)) {
                // 首次逾期
                emLogRepo.save(new EmergencyLog(null, order.getDevice(), overdueEm, now));
                publisher.publishEvent(new EmergencyTriggeredEvent(overdueEm, order.getDevice(), now));
                log.warn("🛠️  RepairOrder {} overdue, emergency triggered", order.getId());
            }

            if (!overdue || finished) {
                active.remove(key); // 清理状态，便于再次逾期重新触发
            }
        });
    }
}
