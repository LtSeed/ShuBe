package org.example.shubackend.entity.work.device.event;

import org.example.shubackend.entity.work.device.Device;

import java.time.Instant;
import java.util.Map;

public record DeviceEventFired(Device device,
                               DeviceEvent meta,
                               Instant timestamp,
                               Map<String, Object> metrics) {
}