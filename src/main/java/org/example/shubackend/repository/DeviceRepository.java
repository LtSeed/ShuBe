package org.example.shubackend.repository;

import org.example.shubackend.entity.work.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findByLocation_LocationNameIn(Collection<String> locations);

}

