package org.example.shubackend.service.area;

import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Triple;
import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.entity.work.device.emergency.EmergencyCode;
import org.example.shubackend.entity.work.device.event.AreaEmergencyTriggeredEvent;
import org.example.shubackend.repository.EmergencyLogRepository;
import org.example.shubackend.service.DeviceService;
import org.example.shubackend.service.device.EmergencyStatusCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaEmergencyService {

    private DeviceService deviceService;
    private EmergencyStatusCache emergencyStatusCache;
    private EmergencyLogRepository emergencyLogRepository;
    private ApplicationEventPublisher eventPublisher;

    // 区域Emergency触发阈值，可通过配置设置，默认为3
    @Value("${emergency.aggregate.threshold:3}")
    private int thresholdCount;

    /**
     * 处理单个设备紧急事件的聚合逻辑：检查邻近区域相同类型的事件数量，必要时触发区域Emergency。
     *
     * @param device 刚触发紧急事件的设备
     * @param type   紧急事件类型
     */
    public void checkAreaEmergency(Device device, EmergencyCode type) {
        // 将当前设备的紧急状态加入缓存（标记其触发了此类型Emergency）
        emergencyStatusCache.markActive(device.getId(), type);

        // 解析设备位置，获取楼层和网格坐标
        Triple<Integer, Integer, Integer> parse = device.getLocation().parse();
        int floor = parse.a;
        int n = parse.b;
        int m = parse.c;

        // 获取同楼层相邻网格内的所有设备
        List<Device> neighborDevices = deviceService.findNearbyDevices(floor, n, m);

        // 统计邻近设备中具有相同Emergency类型的数量
        int count = 0;
        for (Device d : neighborDevices) {
            if (emergencyStatusCache.isActive(d.getId(), type)) {
                count++;
            }
        }

        // 检查是否达到触发区域Emergency的阈值
        if (count >= thresholdCount) {
            // 达到阈值，生成区域Emergency事件
            String areaLocation = floor + "@" + n + "x" + m;
            AreaEmergencyTriggeredEvent areaEvent = new AreaEmergencyTriggeredEvent(areaLocation, type, Instant.now());
            eventPublisher.publishEvent(areaEvent);
        }
    }
}
