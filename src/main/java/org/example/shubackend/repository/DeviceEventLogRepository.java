package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEventLogRepository extends JpaRepository<DeviceEventLog,Long> {}
