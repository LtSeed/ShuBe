package org.example.shubackend.entity.work.device.event;

public enum FireDeviceEventCode {
    WATER_PRESSURE_LOW,     // 消防管网压力低
    TANK_LEVEL_LOW,         // 消防水箱液位低
    PUMP_FAILURE,           // 消防泵故障
    VALVE_OPEN_FAILURE,     // 阀门未能打开
    SPRINKLER_ACTIVATED,    // 喷淋头动作
    SMOKE_DETECTED,         // 烟雾探测
    TEMP_OVER_LIMIT,        // 设备温度过高
    POWER_FAILURE,          // 备用电源故障
    DOOR_NOT_OPEN,
    DOOR_NOT_CLOSE,
}
