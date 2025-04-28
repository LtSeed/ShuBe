package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.emergency.AreaEmergencyLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaEmergencyLogRepository extends JpaRepository<AreaEmergencyLog, Integer> {
}
