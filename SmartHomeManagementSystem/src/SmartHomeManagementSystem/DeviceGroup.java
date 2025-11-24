package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup {
    // 私有属性
    private String name;
    private List<SmartDevice> devices;

    // 构造方法
    public DeviceGroup(String name, List<SmartDevice> devices) {
        this.name = name;
        this.devices = new ArrayList<SmartDevice>();
    }

    // Getter 方法
    public String getName() {
        return name;
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
                switch (state.toLowerCase()) {
                    case "on":
                    case "turnon":
                        device.turnOn();
                        break;
                    case "off":
                    case "turnoff":
                        device.turnOff();
                        break;
                    case "toggle":
                        if (device.isOn()) {
                            device.turnOff();
                        } else {
                            device.turnOn();
                        }
                        break;
                    default:
                        // 如果有自定义状态，可以在这里扩展
                        System.out.println("Unknown state: " + state);
                        break;
                }
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