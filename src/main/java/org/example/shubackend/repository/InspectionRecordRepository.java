package org.example.shubackend.repository;

import org.example.shubackend.entity.work.inspection.InspectionPlan;
import org.example.shubackend.entity.work.inspection.InspectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, Integer> {
    Optional<InspectionRecord> findTopByPlanOrderByInspectionTimeDesc(InspectionPlan plan);
}
