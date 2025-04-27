package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface EmergencyLogRepository extends JpaRepository<EmergencyLog, Long> {
    boolean existsByDeviceAndEmergencyAndTimestampAfter(Device device, Emergency em, Instant instant);
}
