package org.example.shubackend.entity.work.device.emergency;

public enum EmergencyCode {
    FIRE_ALARM,                 // 火警
    WATER_SUPPLY_FAILURE,       // 供水系统失效
    SPRINKLER_SYSTEM_FAILURE,   // 喷淋系统失效
    POWER_OUTAGE,               // 消防供电中断
    SMOKE_EMERGENCY,            // 大量烟雾
    INSPECTION_MISSED,           // 设备巡检逾期
    REPAIR_OVERDUE          // 新增：维修工单逾期
}