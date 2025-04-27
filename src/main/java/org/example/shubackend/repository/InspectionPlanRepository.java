package org.example.shubackend.repository;

import org.example.shubackend.entity.work.InspectionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionPlanRepository extends JpaRepository<InspectionPlan, Integer> {
}
