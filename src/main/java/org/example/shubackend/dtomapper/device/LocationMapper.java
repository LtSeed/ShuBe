// src/main/java/org/example/shubackend/service/dtomapper/device/LocationMapper.java
package org.example.shubackend.dtomapper.device;

import org.example.shubackend.dto.device.LocationDTO;
import org.example.shubackend.entity.work.device.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO toDto(Location entity);

    Location toEntity(LocationDTO dto);
}
