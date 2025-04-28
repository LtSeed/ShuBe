package org.example.shubackend.service.device;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.example.shubackend.entity.work.device.event.EmergencyTriggeredEvent;
import org.example.shubackend.repository.EmergencyPlanCommandRepository;
import org.example.shubackend.service.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmergencyTriggeredEventListener {

    private final EmergencyPlanCommandRepository cmdRepo;
    private final NotificationService noticeService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final DeviceMqttService deviceMqttService;

    @EventListener
    @Transactional
    public void onEmergency(EmergencyTriggeredEvent ev) {

        Emergency em = ev.meta();
        List<EmergencyPlanCommand> cmds = cmdRepo.findByEmergency(em);
        if (cmds.isEmpty()) return;

        for (EmergencyPlanCommand pc : cmds) {
            handleDeviceCommands(pc.getCommands());
            handleWarnings(pc.getWarnings());
        }
    }

    /* ---------- helpers ---------- */

    /**
     * 执行 “设备id@命令” 列表
     */
    private void handleDeviceCommands(String json) {
        if (json == null || json.isBlank()) return;
        try {
            List<String> list = mapper.readValue(json, new TypeReference<>() {
            });
            for (String s : list) {
                String[] parts = s.split("@", 2);
                if (parts.length == 2) {
                    deviceMqttService.sendCommand(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            log.error("parse device commands json failed", e);
        }
    }

    /**
     * 执行 “roleId@类型@描述” 列表
     */
    private void handleWarnings(String json) {
        if (json == null || json.isBlank()) return;
        try {
            List<String> list = mapper.readValue(json, new TypeReference<>() {
            });
            for (String s : list) {
                String[] p = s.split("@", 3);
                if (p.length >= 2) {
                    String roleId = p[0];
                    String warnType = p[1];
                    String desc = p.length == 3 ? p[2] : "";
                    noticeService.sendWarning(roleId, warnType, desc);
                }
            }
        } catch (Exception e) {
            log.error("parse warnings json failed", e);
        }
    }
}
