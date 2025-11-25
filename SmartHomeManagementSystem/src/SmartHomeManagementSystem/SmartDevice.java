package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能设备抽象基类
 * 所有具体智能设备类型（如灯、thermostat等）都应继承此类
 *
 */
public abstract class SmartDevice {
    // 私有属性 - 修改access modifier从protected改为private，符合封装原则
    private String name;
    private String type;
    private String state;

    // 合法状态列表（子类可以覆盖）
    public static String[] legalStates = {"on","off"};

    /**
     * 构造方法
     *
     * @param name 设备名称（唯一标识）
     * @param type 设备类型（如Light, Thermostat, Lock）
     */
    public SmartDevice(String name, String type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Device name cannot be null or empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Device type cannot be null or empty");
        }
        this.name = name;
        this.type = type;
        this.state = "off"; // 默认状态为关闭
    }

    /**
     * 获取设备名称
     *
     * @return 设备名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取设备类型
     *
     * @return 设备类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取设备当前状态
     *
     * @return 设备状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置设备状态
     * 注意：此方法会验证状态的合法性
     *
     * @param state 要设置的状态
     * @throws IllegalArgumentException 如果状态不合法
     */
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        // 状态验证由子类实现（因为每个设备有不同的合法状态）
        this.state = state.toLowerCase();
    }

    /**
     * 打开设备（抽象方法，由子类实现具体逻辑）
     */
    public abstract void turnOn();

    /**
     * 关闭设备（抽象方法，由子类实现具体逻辑）
     */
    public abstract void turnOff();

    /**
     * 检查设备是否处于开启状态
     *
     * @return 如果设备状态为"on"返回true，否则返回false
     */
    public boolean isOn() {
        return "on".equalsIgnoreCase(state);
    }

    /**
     * 切换设备状态（开/关）
     */
    public void toggle() {
        if (isOn()) {
            turnOff();
        } else {
            turnOn();
        }
    }

    /**
     * 返回设备的字符串表示
     *
     * @return 包含设备名称、类型和状态的格式化字符串
     */
    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s'}",
                this.name, this.type, this.state);
    }

    /**
     * 判断两个设备是否相等
     * 基于设备名称和类型判断
     *
     * @param obj 要比较的对象
     * @return 如果设备名称和类型相同返回true
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SmartDevice that = (SmartDevice) obj;
        return name.equals(that.name) && type.equals(that.type);
    }
}