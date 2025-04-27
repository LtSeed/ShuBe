package org.example.shubackend.repository;

import org.example.shubackend.entity.work.MaintenanceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceOrderRepository extends JpaRepository<MaintenanceOrder, Integer> {
}
