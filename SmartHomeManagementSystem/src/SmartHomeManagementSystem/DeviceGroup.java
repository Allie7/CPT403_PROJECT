package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup {
    // 私有属性
    private String name;
    protected List<SmartDevice> devices;
    private String type;

    // 构造方法
    public DeviceGroup(String name, List<SmartDevice> devices) {
        /**
         *check if the devices have the same type(important)
         */
        this.type = devices.get(0).getType();
        this.name = name;
        this.devices = devices;
    }

    // Getter 方法
    public String getName() {
        return name;
    }

    // Getter 方法
    public String getType() {
        return type;
    }
    // 设备管理方法
    public void addDevice(SmartDevice device) {
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }

    public void removeDevice(SmartDevice device) {
        if (device != null) {
            devices.remove(device);
        }
    }

    // 批量操作方法
    public void applyToAll(String state) {
        for (SmartDevice device : devices) {
            if (state != null) {
                // 根据状态字符串执行相应操作
                device.setState(state);
            }
        }
    }
    // 批量操作方法
    public String toString() {
        String toString = "";
        for (SmartDevice device : devices) {
            toString = toString.concat(device.toString()) + " ";
        }
        return toString;
    }

    public void applyToAll(Integer brightness) {
        applyToAll("on");
        for (SmartDevice device : devices) {
            if (device instanceof SmartLight) {
                ((SmartLight) device).setBrightness(brightness);
            }
            else {
                throw new IllegalArgumentException("type not correct");
            }
        }
    }

    public void applyToAll(Double temperature) {
        applyToAll("on");
        for (SmartDevice device : devices) {
            if (device instanceof SmartThermostat) {
                ((SmartThermostat) device).setTemperature(temperature);
            }
            else {
                throw new IllegalArgumentException("type not correct");
            }
        }
    }

    // 辅助方法（虽然不是类图中要求的，但通常很有用）
    public List<SmartDevice> getDevices() {
        return this.devices; // 返回副本以保护内部数据
    }

    public boolean containsDevice(SmartDevice device) {
        return devices.contains(device);
    }

    public int getDeviceCount() {
        return devices.size();
    }

    public void clearDevices() {
        devices.clear();
    }

}