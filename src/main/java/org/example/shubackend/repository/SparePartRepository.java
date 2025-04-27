package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepository extends JpaRepository<SparePart, Integer> {
}
