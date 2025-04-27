package org.example.shubackend.entity.work.common;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.shubackend.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "data")
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "data_type_id", nullable = false)
    private DataType dataType;
    private String data;
}
