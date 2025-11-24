package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
/**
 * 智能设备抽象基类
 * 所有具体智能设备类型（如灯、 thermostat等）都应继承此类
 */
public abstract class SmartDevice {
    // 私有属性
    protected String name;
    protected String type;
    protected String state;
    public static String[] legalStates = {"ON","OFF"};

    // 构造方法
    public SmartDevice(String name, String type) {
        this.name = name;
        this.type = type;
        this.state = "off"; // 默认状态为关闭
    }

    // Getter 方法
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    // Setter 方法
    public void setState(String state) {
        this.state = state;
    }

    // 抽象方法 - 必须由子类实现
    public abstract void turnOn();
    public abstract void turnOff();

    // 辅助方法
    public boolean isOn() {
        return "on".equalsIgnoreCase(state);
    }

    public void toggle() {
        if (isOn()) {
            turnOff();
        } else {
            turnOn();
        }
    }

    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s'}",
                this.name, this.type, this.state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SmartDevice that = (SmartDevice) obj;
        return name.equals(that.name) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + type.hashCode();
    }
}
