package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.device.Location;
import org.example.shubackend.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationCrudService extends GenericCrudService<Location, Integer> {
    public LocationCrudService(LocationRepository r) {
        super(r);
    }
}
