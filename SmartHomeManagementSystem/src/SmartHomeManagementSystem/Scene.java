package SmartHomeManagementSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景类
 * 用于定义和存储预设的设备状态组合配置
 * 例如："电影之夜"场景可能包括调暗客厅灯光、锁定前门等操作
 *
 * 注意：Scene 只是一个配置类，实际的设备控制由 SmartHomeHub 执行
 *
 */
public class Scene {
    // 私有属性
    private String name;
    private String description;
    private Map<String, String> deviceStates; // 设备名称 → 目标状态的映射
    protected String state; // 场景状态（如：active, inactive等）

    /**
     * 构造方法
     * 通过设备状态映射创建场景
     *
     * @param name scene name
     * @param device_states 设备名称到目标状态的映射
     */
    public Scene(String name, Map<String, String> device_states) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty");
        }
        this.name = name;
        this.description = "";
        this.state = "inactive"; // 默认场景状态为未激活

        // 存储设备状态配置（深拷贝以保护数据）
        if (device_states != null) {
            this.deviceStates = new HashMap<>(device_states);
        } else {
            this.deviceStates = new HashMap<>();
        }
    }

    // ==================== 设备状态配置管理 ====================

    /**
     * 添加或更新设备状态配置
     *
     * @param deviceName 设备名称
     * @param targetState 目标状态
     */
    public void addDeviceState(String deviceName, String targetState) {
        if (deviceName == null || deviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("Device name cannot be null or empty");
        }
        if (targetState == null || targetState.trim().isEmpty()) {
            throw new IllegalArgumentException("Target state cannot be null or empty");
        }
        deviceStates.put(deviceName, targetState);
    }

    /**
     * 移除设备状态配置
     *
     * @param deviceName 设备名称
     */
    public void removeDeviceState(String deviceName) {
        if (deviceName != null) {
            deviceStates.remove(deviceName);
        }
    }

    /**
     * 获取指定设备的目标状态
     *
     * @param deviceName 设备名称
     * @return 目标状态，如果设备不在场景中返回null
     */
    public String getDeviceState(String deviceName) {
        return deviceStates.get(deviceName);
    }

    /**
     * 获取所有设备状态配置（返回副本以保护内部数据）
     *
     * @return 设备状态映射的副本
     */
    public Map<String, String> getDeviceStates() {
        return new HashMap<>(deviceStates);
    }

    /**
     * 检查场景中是否包含指定设备
     *
     * @param deviceName 设备名称
     * @return 如果包含该设备返回true
     */
    public boolean containsDevice(String deviceName) {
        return deviceStates.containsKey(deviceName);
    }

    /**
     * 获取场景中的设备数量
     *
     * @return 设备数量
     */
    public int getDeviceCount() {
        return deviceStates.size();
    }

    /**
     * 清空场景中的所有设备配置
     */
    public void clearDeviceStates() {
        deviceStates.clear();
    }

    // ==================== Getter和Setter方法 ====================

    /**
     * 获取场景名称
     *
     * @return scene name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置场景名称
     *
     * @param name scene name
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * 获取场景描述
     *
     * @return 场景描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置场景描述
     *
     * @param description 场景描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取场景状态
     *
     * @return 场景状态（如：active, inactive）
     */
    public String getState() {
        return state;
    }

    /**
     * 设置场景状态
     *
     * @param state 场景状态
     */
    public void setState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene state cannot be null or empty");
        }
        this.state = state;
    }

    /**
     * 返回场景的字符串表示
     *
     * @return 包含场景名称、状态和设备配置的格式化字符串
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scene{name='").append(name).append("', state='").append(state).append("', devices=[");

        int count = 0;
        for (Map.Entry<String, String> entry : deviceStates.entrySet()) {
            if (count > 0) sb.append(", ");
            sb.append(entry.getKey()).append("->").append(entry.getValue());
            count++;
        }

        sb.append("]}");
        return sb.toString();
    }
}