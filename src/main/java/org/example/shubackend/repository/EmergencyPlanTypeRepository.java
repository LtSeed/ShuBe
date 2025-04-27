package org.example.shubackend.repository;

import org.example.shubackend.entity.work.EmergencyPlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyPlanTypeRepository extends JpaRepository<EmergencyPlanType, Integer> {
}
