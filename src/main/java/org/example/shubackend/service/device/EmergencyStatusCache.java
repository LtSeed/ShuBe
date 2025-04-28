package org.example.shubackend.service.device;

import org.example.shubackend.entity.work.device.emergency.EmergencyCode;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存设备当前的 EmergencyCode 状态，线程安全版。
 * <p>
 * • markActive(..)   —— 标记设备触发某个 EmergencyCode（ON）
 * • clear(..)        —— 设备该 EmergencyCode 恢复正常（OFF）
 * • isActive(..)     —— 查询设备当前是不是仍在该 EmergencyCode 状态
 * • activeCodes(..)  —— 返回设备所有激活的 EmergencyCode
 * • lastChanged(..)  —— 最近一次 ON/OFF 时间戳
 */
@Component
public class EmergencyStatusCache {

    /**
     * deviceId → { EmergencyCode → first-trigger timestamp }
     */
    private final Map<Integer, Map<EmergencyCode, Instant>> map = new ConcurrentHashMap<>();

    /* ======== 状态写入 ======== */

    public void markActive(Integer deviceId, EmergencyCode code) {
        Map<EmergencyCode, Instant> devMap = map.computeIfAbsent(
                deviceId, id -> Collections.synchronizedMap(
                        new EnumMap<>(EmergencyCode.class)));

        // 只在首次触发时写入时间戳，避免一直覆盖
        devMap.putIfAbsent(code, Instant.now());
    }

    public void clear(Integer deviceId, EmergencyCode code) {
        Map<EmergencyCode, Instant> devMap = map.get(deviceId);
        if (devMap == null) return;

        devMap.remove(code);
        if (devMap.isEmpty()) map.remove(deviceId);
    }

    /* ======== 状态查询 ======== */

    public boolean isActive(Integer deviceId, EmergencyCode code) {
        Map<EmergencyCode, Instant> devMap = map.get(deviceId);
        return devMap != null && devMap.containsKey(code);
    }

    public Set<EmergencyCode> activeCodes(Integer deviceId) {
        Map<EmergencyCode, Instant> devMap = map.get(deviceId);
        return devMap == null ? Set.of() : Collections.unmodifiableSet(devMap.keySet());
    }

    public Instant lastChanged(Integer deviceId, EmergencyCode code) {
        Map<EmergencyCode, Instant> devMap = map.get(deviceId);
        return devMap == null ? null : devMap.get(code);
    }

    /* ======== 调试用快照 ======== */
    public Map<Integer, Set<EmergencyCode>> snapshot() {
        Map<Integer, Set<EmergencyCode>> snap = new HashMap<>();
        map.forEach((id, m) -> snap.put(id, Set.copyOf(m.keySet())));
        return snap;
    }
}
