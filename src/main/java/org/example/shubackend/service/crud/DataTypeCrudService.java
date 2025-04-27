package org.example.shubackend.service.crud;

import org.example.shubackend.entity.work.common.DataType;
import org.example.shubackend.repository.DataTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class DataTypeCrudService extends GenericCrudService<DataType, Integer> {
    public DataTypeCrudService(DataTypeRepository r) {
        super(r);
    }
}
