package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备组类
 * 用于管理同一类型的多个智能设备，支持批量操作
 *
 */
public class DeviceGroup {
    // 私有属性
    private String name;
    private List<SmartDevice> devices;
    private String type;

    /**
     * 构造方法
     * 创建一个设备组，所有设备必须是同一类型
     *
     * @param name 设备组名称
     * @param devices 设备列表
     * @throws IllegalArgumentException 如果设备列表为空或设备类型不一致
     */
    public DeviceGroup(String name, List<SmartDevice> devices) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        if (devices == null || devices.isEmpty()) {
            throw new IllegalArgumentException("Device list cannot be null or empty");
        }

        // 检查所有设备是否为同一类型
        this.type = devices.get(0).getType();
        for (SmartDevice device : devices) {
            if (!device.getType().equals(this.type)) {
                throw new IllegalArgumentException("All devices in a group must have the same type");
            }
        }

        this.name = name;
        this.devices = new ArrayList<>(devices);
    }

    /**
     * 获取设备组名称
     *
     * @return 设备组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取设备组类型
     *
     * @return 设备组类型
     */
    public String getType() {
        return type;
    }

    /**
     * 向设备组添加设备
     *
     * @param device 要添加的设备
     * @throws IllegalArgumentException 如果设备类型不匹配或设备为null
     */
    public void addDevice(SmartDevice device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null");
        }
        if (!device.getType().equals(this.type)) {
            throw new IllegalArgumentException("Device type must match group type: " + this.type);
        }
        if (!devices.contains(device)) {
            devices.add(device);
        }
    }

    /**
     * 从设备组移除设备
     *
     * @param device 要移除的设备
     */
    public void removeDevice(SmartDevice device) {
        if (device != null) {
            devices.remove(device);
        }
    }

    /**
     * 对组内所有设备应用相同的状态
     *
     * @param state 要应用的状态
     */
    public void applyToAll(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        for (SmartDevice device : devices) {
            device.setState(state);
        }
    }

    /**
     * 对组内所有灯光设备设置亮度
     *
     * @param brightness 亮度值（0-100）
     * @throws IllegalArgumentException 如果设备类型不是Light或亮度值无效
     */
    public void applyToAll(Integer brightness) {
        if (!this.type.equals("Light")) {
            throw new IllegalArgumentException("Brightness can only be set for Light devices");
        }
        applyToAll("on");
        for (SmartDevice device : devices) {
            if (device instanceof SmartLight) {
                ((SmartLight) device).setBrightness(brightness);
            }
        }
    }

    /**
     * 对组内所有温控器设备设置温度
     *
     * @param temperature 温度值（摄氏度）
     * @throws IllegalArgumentException 如果设备类型不是Thermostat或温度值无效
     */
    public void applyToAll(Double temperature) {
        if (!this.type.equals("Thermostat")) {
            throw new IllegalArgumentException("Temperature can only be set for Thermostat devices");
        }
        applyToAll("on");
        for (SmartDevice device : devices) {
            if (device instanceof SmartThermostat) {
                ((SmartThermostat) device).setTemperature(temperature);
            }
        }
    }

    /**
     * 返回设备组的字符串表示
     *
     * @return 包含所有设备信息的字符串
     */
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (SmartDevice device : devices) {
            toString.append(device.toString()).append(" ");
        }
        return toString.toString().trim();
    }

    /**
     * 获取设备列表（返回副本以保护内部数据）
     *
     * @return 设备列表的副本
     */
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(this.devices);
    }

    /**
     * 检查设备组是否包含指定设备
     *
     * @param device 要检查的设备
     * @return 如果包含该设备返回true
     */
    public boolean containsDevice(SmartDevice device) {
        return devices.contains(device);
    }

    /**
     * 获取设备组中的设备数量
     *
     * @return 设备数量
     */
    public int getDeviceCount() {
        return devices.size();
    }

    /**
     * 清空设备组中的所有设备
     */
    public void clearDevices() {
        devices.clear();
    }
}