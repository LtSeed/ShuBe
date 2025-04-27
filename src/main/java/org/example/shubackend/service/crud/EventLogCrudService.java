package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.example.shubackend.repository.DeviceEventLogRepository;
import org.springframework.stereotype.Service;

@Service
public class EventLogCrudService extends GenericCrudService<DeviceEventLog, Long> {
    public EventLogCrudService(DeviceEventLogRepository r) {
        super(r);
    }
}