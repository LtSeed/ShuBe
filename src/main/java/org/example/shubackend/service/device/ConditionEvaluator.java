package org.example.shubackend.service.device;

import org.example.shubackend.entity.work.device.event.DeviceEventTriggerCondition;
import org.example.shubackend.entity.work.device.emergency.EmergencyTriggerCondition;
import org.example.shubackend.entity.work.device.event.DeviceEventFired;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public interface ConditionEvaluator {
    boolean matches(DeviceEventTriggerCondition cond, Map<String, Object> metrics);

    boolean emergencyMatches(EmergencyTriggerCondition cond,
                             Collection<DeviceEventFired> recentEvents,
                             Instant now);
}

