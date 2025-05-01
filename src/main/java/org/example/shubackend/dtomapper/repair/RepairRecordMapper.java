package org.example.shubackend.dtomapper.repair;

import org.example.shubackend.dto.repair.RepairRecordDTO;
import org.example.shubackend.entity.work.repair.RepairRecord;
import org.example.shubackend.repository.DeviceRepository;
import org.example.shubackend.repository.RepairOrderRepository;
import org.example.shubackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {RepairOrderMapper.class})
public abstract class RepairRecordMapper {

    @Autowired
    private RepairOrderRepository orderRepo;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;


    public RepairRecordDTO toDto(RepairRecord e) {
        if (e == null) return null;
        return new RepairRecordDTO(
                e.getId(),
                new RepairOrderMapper() {
                }.toDto(e.getOrder()),
                e.getAction(),
                e.getStartTime(),
                e.getEndTime(),
                e.getCost(),
                e.getPartsUsed()
        );
    }

    @Mapping(target = "id", ignore = true)
    public RepairRecord toEntity(RepairRecordDTO dto) {
        if (dto == null) return null;
        RepairRecord e = new RepairRecord();
        e.setId(dto.id());
        e.setOrder(orderRepo.getReferenceById(dto.order().id()));
        e.setAction(dto.action());
        e.setStartTime(dto.startTime());
        e.setEndTime(dto.endTime());
        e.setCost(dto.cost());
        e.setPartsUsed(dto.partsUsed());
        return e;
    }
}
