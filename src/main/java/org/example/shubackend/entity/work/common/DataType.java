package org.example.shubackend.entity.work.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "data_types")
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataType extends BaseEntity {
    @Column(name = "type_name", nullable = false)
    private String typeName;
    private String unit;
    @Column(columnDefinition = "text")
    private String description;
}