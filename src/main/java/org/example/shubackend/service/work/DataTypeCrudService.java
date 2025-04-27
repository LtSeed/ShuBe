package org.example.shubackend.service.work;

import org.example.shubackend.repository.DataTypeRepository;
import org.example.shubackend.entity.work.DataType;
import org.example.shubackend.service.GenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class DataTypeCrudService extends GenericCrudService<DataType, Integer> {
    public DataTypeCrudService(DataTypeRepository r) {
        super(r);
    }
}
