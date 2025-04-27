package org.example.shubackend.repository;

import org.example.shubackend.entity.work.InspectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectionRecordRepository extends JpaRepository<InspectionRecord, Integer> {
}
