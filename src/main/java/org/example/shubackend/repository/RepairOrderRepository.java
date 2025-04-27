package org.example.shubackend.repository;

import org.example.shubackend.entity.work.repair.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Integer> {
}
