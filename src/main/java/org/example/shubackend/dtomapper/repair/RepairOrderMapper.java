// src/main/java/org/example/shubackend/service/dtomapper/repair/RepairOrderMapper.java
package org.example.shubackend.dtomapper.repair;

import org.example.shubackend.dto.repair.RepairOrderDTO;
import org.example.shubackend.dtomapper.inspection.SimpleDeviceMapper;
import org.example.shubackend.dtomapper.inspection.SimpleUserMapper;
import org.example.shubackend.entity.work.repair.RepairOrder;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {SimpleDeviceMapper.class, SimpleUserMapper.class})
public abstract class RepairOrderMapper {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    public RepairOrderDTO toDto(RepairOrder e) {
        if (e == null) return null;
        return new RepairOrderDTO(
                e.getId(),
                new SimpleDeviceMapper() {
                }.toDto(e.getDevice()),        // MapStruct 也可自动，但写法示意
                new SimpleUserMapper() {
                }.toDto(e.getReporter()),
                new SimpleUserMapper() {
                }.toDto(e.getAssignee()),
                e.getFaultDescription(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getDue()
        );
    }

    @Mapping(target = "id", ignore = true) // id 由 save 时填充
    public RepairOrder toEntity(RepairOrderDTO dto) {
        if (dto == null) return null;
        RepairOrder e = new RepairOrder();
        e.setId(dto.id());
        e.setDevice(deviceRepository.getReferenceById(dto.device().id()));
        e.setReporter(userRepository.getReferenceById(dto.reporter().id()));
        e.setAssignee(userRepository.getReferenceById(dto.assignee().id()));
        e.setFaultDescription(dto.faultDescription());
        e.setStatus(dto.status());
        e.setCreatedAt(dto.createdAt());
        e.setDue(dto.due());
        return e;
    }
}
