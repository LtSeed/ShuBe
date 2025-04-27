package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.DeviceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRoleRepository extends JpaRepository<DeviceRole, Integer> {
}
