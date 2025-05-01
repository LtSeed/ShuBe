package org.example.shubackend.service;

import org.example.shubackend.entity.work.device.Device;
import org.example.shubackend.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;  // 设备数据访问仓库

    /**
     * 查找与指定楼层和坐标相邻的所有设备（包含同位置设备）。
     * 邻居定义：同一楼层，且网格坐标在给定坐标的上下左右或自身（n差值和m差值 ∈ {-1,0,1}）。
     */
    public List<Device> findNearbyDevices(int floor, int n, int m) {
        // 准备邻近坐标集合，包括自身所在坐标和上下左右四个方向
        List<String> neighborLocations = new ArrayList<>();
        // 自身
        neighborLocations.add(floor + "@" + n + "x" + m);
        // 上下左右
        neighborLocations.add(floor + "@" + (n - 1) + "x" + m);  // 上方邻居 (n-1, m)
        neighborLocations.add(floor + "@" + (n + 1) + "x" + m);  // 下方邻居 (n+1, m)
        neighborLocations.add(floor + "@" + n + "x" + (m - 1));  // 左侧邻居 (n, m-1)
        neighborLocations.add(floor + "@" + n + "x" + (m + 1));  // 右侧邻居 (n, m+1)

        // 调用仓库接口，一次性查询这些位置上的所有设备
        return deviceRepository.findByLocation_LocationNameIn(neighborLocations);
    }
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}
