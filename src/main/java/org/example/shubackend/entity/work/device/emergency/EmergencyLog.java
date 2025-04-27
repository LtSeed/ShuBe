package org.example.shubackend.entity.work.device.emergency;

import jakarta.persistence.*;
import lombok.*;
import org.example.shubackend.entity.work.device.Device;

import java.time.Instant;

@Entity
@Table(name = "emergency_log")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class EmergencyLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) private Device device;
    @ManyToOne(optional = false) private Emergency emergency;

    private Instant timestamp;
}