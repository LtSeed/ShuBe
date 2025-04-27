package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.event.DeviceEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceEventRepository extends JpaRepository<DeviceEvent, Integer> {
}
