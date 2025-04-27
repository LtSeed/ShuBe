package org.example.shubackend.service.device;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository repo;

    @Transactional(readOnly = true)
    public Optional<Device> findById(Integer id) {
        return repo.findById(id);
    }

    @Transactional
    public void updateStatus(Device device, Device.Status status) {
        device.setStatus(status);
        repo.save(device);
    }
}