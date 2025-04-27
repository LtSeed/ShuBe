package org.example.shubackend.service.work;

import org.example.shubackend.repository.LocationRepository;
import org.example.shubackend.entity.work.Location;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class LocationCrudService extends GenericCrudService<Location, Integer> {
    public LocationCrudService(LocationRepository r) {
        super(r);
    }
}
