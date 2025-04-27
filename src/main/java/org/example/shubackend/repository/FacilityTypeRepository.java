package org.example.shubackend.repository;

import org.example.shubackend.entity.work.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType, Integer> {
}
