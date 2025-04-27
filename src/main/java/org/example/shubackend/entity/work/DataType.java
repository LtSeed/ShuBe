package org.example.shubackend.entity.work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "data_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataType extends BaseEntity {
    @Column(name = "type_name", nullable = false)
    private String typeName;
    @Column(name = "data_type", nullable = false)
    private String dataType;
    private String unit;
    @Column(columnDefinition = "text")
    private String description;
}