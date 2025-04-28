package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.emergency.Emergency;
import org.example.shubackend.entity.work.device.emergency.EmergencyPlanCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyPlanCommandRepository
        extends JpaRepository<EmergencyPlanCommand, Integer> {

    List<EmergencyPlanCommand> findByEmergency(Emergency emergency);
}
