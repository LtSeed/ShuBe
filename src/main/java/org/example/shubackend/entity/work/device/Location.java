package org.example.shubackend.entity.work.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends BaseEntity {
    @Column(name = "location_name", nullable = false)
    private String locationName;
    private String description;
}