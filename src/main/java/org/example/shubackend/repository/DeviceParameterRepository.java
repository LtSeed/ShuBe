package org.example.shubackend.repository;

import org.example.shubackend.entity.work.DeviceParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceParameterRepository extends JpaRepository<DeviceParameter, Integer> {
}
