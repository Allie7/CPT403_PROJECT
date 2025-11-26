package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 智能家居中枢类
 * 系统的核心控制器，负责管理所有设备、设备组和场景
 *
 */
public class SmartHomeHub {
    private List<DeviceGroup> groups;
    private List<SmartDevice> devices;
    private List<Scene> scenes;

    /**
     * 构造方法（带参数）
     *
     * @param groups 设备组列表
     * @param devices 设备列表
     * @param scenes 场景列表
     */
    protected SmartHomeHub(List<DeviceGroup> groups, List<SmartDevice> devices, List<Scene> scenes) {
        this.groups = groups != null ? new ArrayList<>(groups) : new ArrayList<>();
        this.devices = devices != null ? new ArrayList<>(devices) : new ArrayList<>();
        this.scenes = scenes != null ? new ArrayList<>(scenes) : new ArrayList<>();
    }

    /**
     * 默认构造方法
     * 创建一个空的智能家居中枢
     */
    protected SmartHomeHub() {
        this.devices = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.scenes = new ArrayList<>();
    }

    // ==================== 设备管理方法 ====================

    /**
     * 通过名称添加设备到中枢
     *
     * @param name 设备名称
     */
    protected void addDevice(String name) {
        SmartDevice device = findDeviceByName(name);
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }

    /**
     * 添加设备到中枢
     *
     * @param device 要添加的设备
     */
    protected void addDevice(SmartDevice device) {
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }

    /**
     * 通过名称移除设备
     * 同时从所有包含该设备的设备组中移除
     *
     * @param name 设备名称
     */
    protected void removeDevice(String name) {
        SmartDevice device = findDeviceByName(name);
        if (device != null) {
            devices.remove(device);
            for (DeviceGroup group : groups) {
                if (group.containsDevice(device)) {
                    group.removeDevice(device);
                }
            }
        }
    }

    /**
     * 移除设备
     *
     * @param device 要移除的设备
     */
    protected void removeDevice(SmartDevice device) {
        if (device != null) {
            devices.remove(device);
        }
    }

    /**
     * 通过名称查找设备
     *
     * @param name 设备名称
     * @return 找到的设备，如果不存在返回null
     */
    protected SmartDevice findDeviceByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    // ==================== 设备组管理方法 ====================

    /**
     * 添加设备组到中枢
     * 如果设备组中的设备不在中枢中，会自动添加
     *
     * @param group 要添加的设备组
     */
    protected void addGroup(DeviceGroup group) {
        if (group != null && !groups.contains(group)) {
            for (SmartDevice device : group.getDevices()) {
                if (!devices.contains(device)) {
                    devices.add(device);
                }
            }
            groups.add(group);
        }
    }

    /**
     * 通过名称移除设备组
     *
     * @param group_name 设备组名称
     */
    protected void removeGroup(String group_name) {
        DeviceGroup group = getGroupByName(group_name);
        if (group != null) {
            groups.remove(group);
        }
    }

    /**
     * 移除设备组
     *
     * @param group 要移除的设备组
     */
    protected void removeGroup(DeviceGroup group) {
        if (group != null) {
            groups.remove(group);
        }
    }

    /**
     * 通过名称获取设备组
     *
     * @param name 设备组名称
     * @return 找到的设备组，如果不存在返回null
     */
    public DeviceGroup getGroupByName(String name) {
        for (DeviceGroup group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    // ==================== 场景管理方法 ====================

    /**
     * 添加场景到中枢
     *
     * @param scene 要添加的场景
     */
    public void addScene(Scene scene) {
        if (scene != null && !scenes.contains(scene)) {
            scenes.add(scene);
        }
    }

    /**
     * 通过名称获取场景
     *
     * @param name 场景名称
     * @return 找到的场景，如果不存在返回null
     */
    public Scene getSceneByName(String name) {
        for (Scene scene : scenes) {
            if (scene.getName().equals(name)) {
                return scene;
            }
        }
        return null;
    }
    
    /**
     * 执行指定名称的场景
     * 根据场景配置，查找设备并设置为目标状态
     *
     * @param name 场景名称
     */
    public void executeScene(String name) {
        Scene scene = getSceneByName(name);
        if (scene != null) {
            // 标记场景为激活状态
            scene.setState("active");

            // 遍历场景中的所有设备配置
            Map<String, String> deviceStates = scene.getDeviceStates();
            for (Map.Entry<String, String> entry : deviceStates.entrySet()) {
                String deviceName = entry.getKey();
                String targetState = entry.getValue();

                // 通过 Hub 查找设备
                SmartDevice device = findDeviceByName(deviceName);
                if (device != null) {
                    try {
                        // 设置设备状态
                        device.setState(targetState);
                    } catch (IllegalArgumentException e) {
                        // 如果状态不合法，记录错误但继续执行其他设备
                        System.err.println("Failed to set " + deviceName + " to " + targetState + ": " + e.getMessage());
                    }
                } else {
                    // 设备不存在，记录警告
                    System.err.println("Device not found in scene '" + name + "': " + deviceName);
                }
            }

            // 执行完成后可以将状态改回 inactive（可选）
            // scene.setState("inactive");
        } else {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
    }

    // ==================== Getter方法 ====================

    /**
     * 获取所有设备列表（返回副本）
     *
     * @return 设备列表的副本
     */
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(devices);
    }

    /**
     * 获取所有设备组列表（返回副本）
     *
     * @return 设备组列表的副本
     */
    public List<DeviceGroup> getGroups() {
        return new ArrayList<>(groups);
    }

    /**
     * 获取所有场景列表（返回副本）
     *
     * @return 场景列表的副本
     */
    public List<Scene> getScenes() {
        return new ArrayList<>(scenes);
    }

    // ==================== 查看方法 ====================

    /**
     * 显示所有设备的信息
     */
    protected void viewAllDevices() {
        for (SmartDevice device : devices) {
            System.out.println(device.toString());
        }
    }

    /**
     * 显示所有设备组的信息
     */
    protected void viewAllGroups() {
        for (DeviceGroup group : groups) {
            System.out.println("Group " + group.getName() + " : " + group.toString());
        }
    }

    // ==================== 设备控制方法 ====================

    /**
     * 控制单个设备的状态
     *
     * @param name 设备名称
     * @param state 目标状态
     * @throws IllegalArgumentException 如果设备不存在
     */
    protected void controlDevice(String name, String state) {
        state = state.toLowerCase();
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null) {
            currentDevice.setState(state);
        } else {
            throw new IllegalArgumentException("Device " + name + " not found");
        }
    }

    /**
     * 设置灯光设备的亮度
     *
     * @param name 设备名称
     * @param brightness 亮度值（0-100）
     * @throws IllegalArgumentException 如果设备不存在或不是灯光设备
     */
    protected void controlDevice(String name, int brightness) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartLight) {
            currentDevice.turnOn();
            ((SmartLight) currentDevice).setBrightness(brightness);
        } else {
            throw new IllegalArgumentException("Light " + name + " not found");
        }
    }

    /**
     * 设置温控器设备的温度
     *
     * @param name 设备名称
     * @param temperature 目标温度
     * @throws IllegalArgumentException 如果设备不存在或不是温控器设备
     */
    protected void controlDevice(String name, Double temperature) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartThermostat) {
            currentDevice.turnOn();
            ((SmartThermostat) currentDevice).setTemperature(temperature);
        } else {
            throw new IllegalArgumentException("Thermostat " + name + " not found");
        }
    }

    // ==================== 设备组控制方法 ====================

    /**
     * 管理设备组状态
     *
     * @param name 设备组名称
     * @param state 目标状态
     * @throws IllegalArgumentException 如果设备组不存在
     */
    public void manageGroup(String name, String state) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null) {
            currentGroup.applyToAll(state);
        } else {
            throw new IllegalArgumentException("Group " + name + " not found");
        }
    }

    /**
     * 设置温控器设备组的温度
     *
     * @param name 设备组名称
     * @param temperature 目标温度
     * @throws IllegalArgumentException 如果设备组不存在或类型不匹配
     */
    public void manageGroup(String name, Double temperature) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType().equals("Thermostat")) {
            currentGroup.applyToAll("on");
            currentGroup.applyToAll(temperature);
        } else {
            throw new IllegalArgumentException("Thermostat group " + name + " not found");
        }
    }

    /**
     * 设置灯光设备组的亮度
     *
     * @param name 设备组名称
     * @param brightness 亮度值（0-100）
     * @throws IllegalArgumentException 如果设备组不存在或类型不匹配
     */
    public void manageGroup(String name, int brightness) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType().equals("Light")) {
            currentGroup.applyToAll("on");
            currentGroup.applyToAll(brightness);
        } else {
            throw new IllegalArgumentException("Light group " + name + " not found");
        }
    }

    // ==================== 查看设备状态方法 ====================

    /**
     * 查看单个设备的详细状态
     *
     * @param currentDevice 要查看的设备
     * @throws IllegalArgumentException 如果设备不存在
     */
    public void viewSingleDevice(SmartDevice currentDevice) {
        if (currentDevice != null) {
            System.out.print(currentDevice.getName() + " is " + currentDevice.getState());
            if (currentDevice instanceof SmartLight && currentDevice.getState().equals("on")) {
                System.out.println(" and current brightness is " + ((SmartLight) currentDevice).getBrightness());
            } else if (currentDevice instanceof SmartThermostat && currentDevice.getState().equals("on")) {
                System.out.println(" and current temperature is " + ((SmartThermostat) currentDevice).getTemperature());
            } else {
                System.out.println("");
            }
        } else {
            throw new IllegalArgumentException("Device not found");
        }
    }

    /**
     * 查看设备状态
     * 支持查看单个设备、设备组或所有设备（使用"ALL"）
     *
     * @param name 设备名称、设备组名称或"ALL"
     * @throws IllegalArgumentException 如果指定的名称不存在
     */
    public void viewDeviceState(String name) {
        if (name.equals("ALL")) {
            for (SmartDevice currentDevice : devices) {
                viewSingleDevice(currentDevice);
            }
        } else {
            if (findDeviceByName(name) != null) {
                SmartDevice currentDevice = findDeviceByName(name);
                viewSingleDevice(currentDevice);
            } else if (getGroupByName(name) != null) {
                DeviceGroup currentGroup = getGroupByName(name);
                if (currentGroup != null) {
                    System.out.println(name + " member states:");
                    for (SmartDevice currentDevice : currentGroup.getDevices()) {
                        viewSingleDevice(currentDevice);
                    }
                }
            } else {
                throw new IllegalArgumentException("Name " + name + " not found");
            }
        }
    }
}