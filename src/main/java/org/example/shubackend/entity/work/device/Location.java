package org.example.shubackend.entity.work.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.antlr.v4.runtime.misc.Triple;
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

    public Triple<Integer, Integer, Integer> parse() throws IllegalArgumentException {
        if (locationName == null || !locationName.contains("@") || !locationName.contains("x")) {
            throw new IllegalArgumentException("无效的Location格式: " + locationName);
        }
        try {
            // 按约定格式拆分楼层和网格
            String[] parts = locationName.split("@");
            int floor = Integer.parseInt(parts[0]);
            String grid = parts[1];           // 例如 "3x5"
            String[] nm = grid.split("x");
            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);
            return new Triple<>(floor, n, m);
        } catch (Exception e) {
            throw new IllegalArgumentException("解析Location失败: " + locationName, e);
        }
    }
}