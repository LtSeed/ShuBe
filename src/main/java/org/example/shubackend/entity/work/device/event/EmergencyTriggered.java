package org.example.shubackend.entity.work.device.event;

import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.device.emergency.Emergency;

import java.time.Instant;

public record EmergencyTriggered(Emergency meta,
                                 Device device,
                                 Instant timestamp) {}