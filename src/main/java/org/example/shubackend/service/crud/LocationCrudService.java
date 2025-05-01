package org.example.shubackend.service.crud;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.dto.device.LocationDTO;
import org.example.shubackend.dtomapper.device.LocationMapper;
import org.example.shubackend.entity.work.device.Location;
import org.example.shubackend.repository.LocationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationCrudService extends GenericCrudService<Location, Integer> {

    private final LocationRepository repo;
    private final LocationMapper mapper;

    protected LocationCrudService(JpaRepository<Location, Integer> repo, LocationRepository repo1, LocationMapper mapper) {
        super(repo);
        this.repo = repo1;
        this.mapper = mapper;
    }

    public List<LocationDTO> findAllDto() {
        return repo.findAll()
                   .stream()
                   .map(mapper::toDto)
                   .collect(Collectors.toList());
    }

    public Optional<LocationDTO> findDto(Integer id) {
        return repo.findById(id).map(mapper::toDto);
    }

    @Transactional
    public LocationDTO createDto(LocationDTO dto) {
        Location saved = repo.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public LocationDTO updateDto(Integer id, LocationDTO dto) {
        Location e = mapper.toEntity(dto);
        e.setId(id);
        Location saved = repo.save(e);
        return mapper.toDto(saved);
    }
}
