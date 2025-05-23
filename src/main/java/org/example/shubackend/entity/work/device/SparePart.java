package org.example.shubackend.entity.work.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "spare_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SparePart extends BaseEntity {
    @Column(name = "part_name", nullable = false)
    private String partName;
    private Integer quantity;
    private Integer minQuantity;
}