package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户类
 * 代表智能家居系统的用户，提供与SmartHomeHub交互的接口
 *
 */
public class User {
    private String username;
    private SmartHomeHub hub;

    /**
     * 构造方法（带参数）
     *
     * @param username 用户名
     * @param hub 智能家居中枢
     */
    public User(String username, SmartHomeHub hub) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
        this.hub = hub != null ? hub : new SmartHomeHub();
    }

    /**
     * 构造方法（仅用户名）
     *
     * @param username 用户名
     */
    public User(String username) {
        this(username, new SmartHomeHub());
    }

    /**
     * 默认构造方法
     */
    public User() {
        this.username = "DefaultUser";
        this.hub = new SmartHomeHub();
    }

    // ==================== 查看方法 ====================

    /**
     * 查看所有设备
     */
    public void viewAllDevices() {
        hub.viewAllDevices();
    }

    /**
     * 查看所有设备组
     */
    public void viewAllGroups() {
        hub.viewAllGroups();
    }

    /**
     * 查看指定设备或设备组的状态
     *
     * @param device_name 设备名称、设备组名称或"ALL"
     */
    public void viewDeviceState(String device_name) {
        hub.viewDeviceState(device_name);
    }

    /**
     * 直接查看设备状态（设备不在Hub中也可查看）
     *
     * @param currentDevice 设备对象
     */
    public void viewDeviceState(SmartDevice currentDevice) {
        hub.viewSingleDevice(currentDevice);
    }

    /**
     * 查看设备组状态
     *
     * @param group 设备组对象
     */
    public void viewDeviceState(DeviceGroup group) {
        hub.viewDeviceState(group.getName());
    }

    // ==================== 设备控制方法（通过Hub） ====================

    /**
     * 控制设备状态
     *
     * @param name 设备名称
     * @param state 目标状态
     */
    public void controlDevices(String name, String state) {
        hub.controlDevice(name, state);
    }

    /**
     * 控制灯光设备亮度
     *
     * @param name 设备名称
     * @param num 亮度值（0-100）
     */
    public void controlDevices(String name, int num) {
        hub.controlDevice(name, num);
    }

    /**
     * 控制温控器设备温度
     *
     * @param name 设备名称
     * @param num 温度值
     */
    public void controlDevices(String name, double num) {
        hub.controlDevice(name, num);
    }

    // ==================== 设备快捷控制方法（通过Hub） ====================

    /**
     * 打开设备（通过设备名称）
     *
     * @param device_name 设备名称
     */
    public void turnOnDevice(String device_name) {
        hub.controlDevice(device_name, "on");
    }

    /**
     * 关闭设备（通过设备名称）
     *
     * @param device_name 设备名称
     */
    public void turnOffDevice(String device_name) {
        hub.controlDevice(device_name, "off");
    }

    /**
     * 锁定设备（通过设备名称）
     *
     * @param device_name 设备名称
     */
    public void lockDevice(String device_name) {
        hub.controlDevice(device_name, "locked");
    }

    /**
     * 解锁设备（通过设备名称）
     *
     * @param device_name 设备名称
     */
    public void unlockDevice(String device_name) {
        hub.controlDevice(device_name, "unlocked");
    }

    // ==================== 直接设备控制方法（不通过Hub） ====================

    /**
     * 直接打开设备（不通过Hub）
     *
     * @param device 设备对象
     */
    public void turnOnDevice(SmartDevice device) {
        device.turnOn();
    }

    /**
     * 直接关闭设备（不通过Hub）
     *
     * @param device 设备对象
     */
    public void turnOffDevice(SmartDevice device) {
        device.turnOff();
    }

    /**
     * 直接锁定设备（不通过Hub）
     *
     * @param device 设备对象
     * @throws IllegalArgumentException 如果设备不是SmartLock
     */
    public void lockDevice(SmartDevice device) {
        if (device instanceof SmartLock) {
            ((SmartLock) device).lock();
        } else {
            throw new IllegalArgumentException(device.getName() + " is not a SmartLock");
        }
    }

    /**
     * 直接解锁设备（不通过Hub）
     *
     * @param device 设备对象
     * @throws IllegalArgumentException 如果设备不是SmartLock
     */
    public void unlockDevice(SmartDevice device) {
        if (device instanceof SmartLock) {
            ((SmartLock) device).unlock();
        } else {
            throw new IllegalArgumentException(device.getName() + " is not a SmartLock");
        }
    }

    // ==================== 设备管理方法 ====================

    /**
     * 通过名称添加设备到中枢
     *
     * @param device_name 设备名称
     */
    public void addDeviceToHub(String device_name) {
        hub.addDevice(device_name);
    }

    /**
     * 添加设备到中枢
     *
     * @param device 设备对象
     */
    public void addDeviceToHub(SmartDevice device) {
        hub.addDevice(device);
    }

    /**
     * 从中枢移除设备
     *
     * @param device_name 设备名称
     */
    public void removeDeviceFromHub(String device_name) {
        hub.removeDevice(device_name);
    }

    // ==================== 设备组管理方法 ====================

    /**
     * 创建设备组
     * 所有设备必须是同一类型
     *
     * @param name 设备组名称
     * @param devices 设备列表
     * @throws IllegalArgumentException 如果设备类型不匹配
     */
    public void createGroup(String name, List<SmartDevice> devices) {
        if (devices == null || devices.isEmpty()) {
            throw new IllegalArgumentException("Device list cannot be null or empty");
        }

        String checkType = devices.get(0).getType();
        for (SmartDevice device : devices) {
            if (!device.getType().equals(checkType)) {
                throw new IllegalArgumentException("All devices in a group must have the same type");
            }
        }
        DeviceGroup newGroup = new DeviceGroup(name, devices);
        hub.addGroup(newGroup);
    }

    /**
     * 通过名称添加设备组到中枢
     *
     * @param device_name 设备组名称
     */
    public void addGroupToHub(String device_name) {
        hub.addDevice(device_name);
    }

    /**
     * 从中枢移除设备组
     *
     * @param group_name 设备组名称
     */
    public void removeGroupFromHub(String group_name) {
        hub.removeGroup(group_name);
    }

    /**
     * 从中枢移除设备组
     *
     * @param group 设备组对象
     */
    public void removeGroupFromHub(DeviceGroup group) {
        hub.removeGroup(group);
    }

    // ==================== 设备组控制方法 ====================

    /**
     * 管理设备组状态
     *
     * @param name 设备组名称
     * @param state 目标状态
     */
    public void manageGroup(String name, String state) {
        hub.manageGroup(name, state);
    }

    /**
     * 设置灯光设备组亮度
     *
     * @param name 设备组名称
     * @param brightness 亮度值（0-100）
     */
    public void manageGroup(String name, int brightness) {
        hub.manageGroup(name, brightness);
    }

    /**
     * 设置温控器设备组温度
     *
     * @param name 设备组名称
     * @param temperature 温度值
     */
    public void manageGroup(String name, double temperature) {
        hub.manageGroup(name, temperature);
    }

    // ==================== 设备组快捷控制方法 ====================

    /**
     * 打开设备组
     *
     * @param group_name 设备组名称
     */
    public void turnOnGroup(String group_name) {
        hub.manageGroup(group_name, "on");
    }

    /**
     * 关闭设备组
     *
     * @param group_name 设备组名称
     */
    public void turnOffGroup(String group_name) {
        hub.manageGroup(group_name, "off");
    }

    /**
     * 锁定设备组
     *
     * @param group_name 设备组名称
     */
    public void lockGroup(String group_name) {
        hub.manageGroup(group_name, "locked");
    }

    /**
     * 解锁设备组
     *
     * @param group_name 设备组名称
     */
    public void unlockGroup(String group_name) {
        hub.manageGroup(group_name, "unlocked");
    }

    // ==================== 场景管理方法 ====================

    /**
     * 创建场景
     *
     * @param name 场景名称
     * @param device_states 设备名称到目标状态的映射
     * @return 创建的场景对象
     */
    public Scene createScene(String name, Map<String, String> device_states) {
        // 直接创建场景（Scene 现在只是配置文件）
        Scene newScene = new Scene(name, device_states);

        // 添加到 Hub
        hub.addScene(newScene);

        return newScene;
    }

    /**
     * 修改场景
     * 更新场景中的设备状态配置
     *
     * @param name 场景名称
     * @param device_states 设备名称到目标状态的映射
     */
    public void modifyScene(String name, Map<String, String> device_states) {
        Scene scene = hub.getSceneByName(name);
        if (scene != null) {
            // 清空原有配置
            scene.clearDeviceStates();

            // 添加新配置
            if (device_states != null) {
                for (Map.Entry<String, String> entry : device_states.entrySet()) {
                    scene.addDeviceState(entry.getKey(), entry.getValue());
                }
            }
        } else {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
    }

    /**
     * 向现有场景添加设备状态
     *
     * @param sceneName 场景名称
     * @param deviceName 设备名称
     * @param targetState 目标状态
     */
    public void addDeviceToScene(String sceneName, String deviceName, String targetState) {
        Scene scene = hub.getSceneByName(sceneName);
        if (scene != null) {
            scene.addDeviceState(deviceName, targetState);
        } else {
            throw new IllegalArgumentException("Scene not found: " + sceneName);
        }
    }

    /**
     * 从场景中移除设备
     *
     * @param sceneName 场景名称
     * @param deviceName 设备名称
     */
    public void removeDeviceFromScene(String sceneName, String deviceName) {
        Scene scene = hub.getSceneByName(sceneName);
        if (scene != null) {
            scene.removeDeviceState(deviceName);
        } else {
            throw new IllegalArgumentException("Scene not found: " + sceneName);
        }
    }



    /**
     * 执行场景
     *
     * @param name 场景名称
     */
    public void runScene(String name) {
        hub.executeScene(name);
    }
}