/* DeviceEventCrudService */
package org.example.shubackend.service.crud;

import org.example.shubackend.dto.device.DeviceEventDTO;
import org.example.shubackend.entity.work.device.event.DeviceEvent;
import org.example.shubackend.repository.DeviceEventRepository;
import org.example.shubackend.dtomapper.device.DeviceEventMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceEventCrudService
        extends GenericCrudService<DeviceEvent, Integer> {

    private final DeviceEventRepository repo;
    private final DeviceEventMapper mapper;

    protected DeviceEventCrudService(JpaRepository<DeviceEvent, Integer> repo, DeviceEventRepository repo1, DeviceEventMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<DeviceEventDTO> findAllDto() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<DeviceEventDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public DeviceEventDTO createDto(DeviceEventDTO d) {
        return mapper.toDto(repo.save(mapper.toEntity(d)));
    }

    @Transactional
    public DeviceEventDTO updateDto(Integer id, DeviceEventDTO d) {
        var e = mapper.toEntity(d);
        e.setId(id);
        return mapper.toDto(repo.save(e));
    }
}
