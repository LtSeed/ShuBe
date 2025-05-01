package org.example.shubackend.dtomapper.inspection;

import org.example.shubackend.dto.SimpleDeviceDTO;
import org.example.shubackend.dto.SimpleUserDTO;
import org.example.shubackend.dto.inspection.InspectionPlanDTO;
import org.example.shubackend.entity.User;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.inspection.InspectionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InspectionPlanMapper {

    // 实体 ➜ DTO
    @Mapping(target = "frequency", source = "frequency")      // enum -> name()
    @Mapping(target = "status", source = "status")
    InspectionPlanDTO toDto(InspectionPlan plan);

    // 如果需要批量
    List<InspectionPlanDTO> toDtoList(List<InspectionPlan> list);

    /* Device & User 子映射 */
    default SimpleDeviceDTO toDeviceDto(Device d) {
        return new SimpleDeviceDTO(d.getId(), d.getDeviceName());
    }

    default SimpleUserDTO toUserDto(User u) {
        return new SimpleUserDTO(u.getId(), u.getUsername());
    }
}
