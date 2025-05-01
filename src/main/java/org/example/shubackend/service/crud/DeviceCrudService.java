package org.example.shubackend.service.crud;

import org.example.shubackend.dto.device.DeviceDTO;
import org.example.shubackend.dtomapper.device.DeviceMapper;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceCrudService extends GenericCrudService<Device, Integer> {

    private final DeviceRepository repo;
    private final DeviceMapper mapper;

    protected DeviceCrudService(JpaRepository<Device, Integer> repo, DeviceRepository repo1, DeviceMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<DeviceDTO> findAllDto() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<DeviceDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public DeviceDTO createDto(DeviceDTO dto) {
        Device saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public DeviceDTO updateDto(Integer id, DeviceDTO dto) {
        Device e = mapper.toEntity(dto);
        e.setId(id);
        Device saved = repo.save(e);
        return mapper.toDto(saved);
    }
}
