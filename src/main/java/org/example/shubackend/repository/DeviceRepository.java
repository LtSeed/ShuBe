package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}

