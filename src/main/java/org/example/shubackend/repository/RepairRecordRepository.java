package org.example.shubackend.repository;

import org.example.shubackend.entity.work.repair.RepairRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRecordRepository extends JpaRepository<RepairRecord, Integer> {
}
