package org.example.shubackend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    /**
     * 发送告警给指定角色。实际项目可接入短信、邮件、IM。
     */
    public void sendWarning(String roleId, String warnType, String desc) {
        // TODO: 接入真实告警渠道
        log.warn("🔔 WARN to role {}  type={}  {}", roleId, warnType, desc);
    }
}
