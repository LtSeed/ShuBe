package org.example.shubackend.dtomapper.inspection;

import org.example.shubackend.dto.SimpleUserDTO;
import org.example.shubackend.entity.User;
import org.example.shubackend.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SimpleUserMapper {

    /* === JPA === */
    @Autowired
    protected UserRepository userRepo;

    /* ---------- entity -> dto ---------- */
    @Named("toUserDto")
    public SimpleUserDTO toDto(User entity) {
        return entity == null ? null
                : new SimpleUserDTO(entity.getId(), entity.getUsername());
    }

    /* ---------- dto -> entity ---------- */
    public User toEntity(SimpleUserDTO dto) {
        if (dto == null) return null;
        return userRepo.getReferenceById(dto.id());
    }
}
