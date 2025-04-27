package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {
    Optional<Emergency> findByCode(EmergencyCode emergencyCode);
}
