package org.example.shubackend.entity.work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "facility_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityType extends BaseEntity {
    @Column(name = "type_name", nullable = false)
    private String typeName;
    @Column(columnDefinition = "text")
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
}