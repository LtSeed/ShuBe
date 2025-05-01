/* DeviceEventLogCrudService */
package org.example.shubackend.service.crud;

import org.example.shubackend.dto.device.DeviceEventLogDTO;
import org.example.shubackend.entity.work.device.event.DeviceEventLog;
import org.example.shubackend.repository.DeviceEventLogRepository;
import org.example.shubackend.dtomapper.device.DeviceEventLogMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceEventLogCrudService
        extends GenericCrudService<DeviceEventLog, Long> {

    private final DeviceEventLogRepository repo;
    private final DeviceEventLogMapper mapper;

    protected DeviceEventLogCrudService(JpaRepository<DeviceEventLog, Long> repo, DeviceEventLogRepository repo1, DeviceEventLogMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<DeviceEventLogDTO> findAllDto() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<DeviceEventLogDTO> findDto(Long id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public DeviceEventLogDTO createDto(DeviceEventLogDTO d) {
        return mapper.toDto(repo.save(mapper.toEntity(d)));
    }
}
