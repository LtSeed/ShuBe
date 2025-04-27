package org.example.shubackend.repository;

import org.example.shubackend.entity.work.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Integer> {
}
