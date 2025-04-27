package org.example.shubackend.entity.work.device.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shubackend.entity.work.device.Device;

import java.time.Instant;

@Entity
@Table(name = "device_event_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Device device;
    @ManyToOne(optional = false)
    private DeviceEvent event;

    private Instant timestamp;          // first time fired “ON”
}