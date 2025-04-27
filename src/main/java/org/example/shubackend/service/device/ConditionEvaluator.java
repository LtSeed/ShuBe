package org.example.shubackend.service.device;

import org.example.shubackend.entity.work.device.emergency.EmergencyTriggerCondition;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;
import org.example.shubackend.entity.work.device.event.DeviceEventTriggerCondition;

import java.time.Instant;
import java.util.Collection;
import java.util.Deque;
import java.util.function.Function;

public interface ConditionEvaluator {
    boolean matches(DeviceEventTriggerCondition cond,
                    Integer deviceId,
                    Function<String, Deque<TelemetryProcessor.MetricPoint>> tsSupplier);

    boolean emergencyMatches(EmergencyTriggerCondition cond,
                             Collection<DeviceEventFired> recentEvents,
                             Instant now);
}

