package org.example.shubackend.repository;

import org.example.shubackend.entity.work.EmergencyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyPlanRepository extends JpaRepository<EmergencyPlan, Integer> {
}
