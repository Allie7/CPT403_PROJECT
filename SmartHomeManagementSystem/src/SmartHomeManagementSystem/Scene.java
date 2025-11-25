package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 场景类
 * 用于定义和执行预设的设备状态组合
 * 例如："电影之夜"场景可能包括调暗客厅灯光、锁定前门等操作
 *
 */
public class Scene {
    // 私有属性
    private String name;
    private String description;
    private List<SceneAction> actions;
    protected String state; // 场景状态（如：active, inactive等）

    /**
     * 构造方法
     * 通过设备状态映射创建场景
     *
     * @param name 场景名称
     * @param device_states 设备名称到目标状态的映射
     */
    public Scene(String name, Map<String, String> device_states) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty");
        }
        this.name = name;
        this.description = "";
        this.state = "inactive"; // 默认场景状态为未激活
        this.actions = new ArrayList<>();

        // 根据device_states构建场景动作列表
        // 注意：这里只创建了actions列表，实际的设备引用需要在执行时通过Hub获取
        if (device_states != null) {
            // 预留：可以在这里存储设备名称和状态的映射
            // 实际执行时需要通过Hub查找设备
        }
    }

    // ==================== 场景动作管理 ====================

    /**
     * 添加场景动作
     *
     * @param action 要添加的场景动作
     */
    public void addAction(SceneAction action) {
        if (action != null && !actions.contains(action)) {
            actions.add(action);
        }
    }

    /**
     * 移除场景动作
     *
     * @param action 要移除的场景动作
     */
    public void removeAction(SceneAction action) {
        if (action != null) {
            actions.remove(action);
        }
    }

    /**
     * 执行场景
     * 按顺序执行场景中的所有动作
     */
    public void execute() {
        this.state = "active"; // 标记场景为激活状态
        for (SceneAction action : actions) {
            action.execute();
        }
    }

    // ==================== Getter和Setter方法 ====================

    /**
     * 获取场景名称
     *
     * @return 场景名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置场景名称
     *
     * @param name 场景名称
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
     * 获取场景动作列表（返回副本以保护内部数据）
     *
     * @return 场景动作列表的副本
     */
    public List<SceneAction> getActions() {
        return new ArrayList<>(actions);
    }

    // ==================== SceneAction内部类 ====================

    /**
     * 场景动作内部类
     * 表示场景中对单个设备的操作
     */
    public class SceneAction {
        // 私有属性
        private SmartDevice device;
        private String desiredState;

        /**
         * 构造方法
         *
         * @param device 要操作的设备
         * @param desiredState 目标状态
         */
        public SceneAction(SmartDevice device, String desiredState) {
            if (device == null) {
                throw new IllegalArgumentException("Device cannot be null");
            }
            if (desiredState == null || desiredState.trim().isEmpty()) {
                throw new IllegalArgumentException("Desired state cannot be null or empty");
            }
            this.device = device;
            this.desiredState = desiredState;
        }

        /**
         * 获取设备
         *
         * @return 设备对象
         */
        public SmartDevice getDevice() {
            return device;
        }

        /**
         * 获取目标状态
         *
         * @return 目标状态
         */
        public String getDesiredState() {
            return desiredState;
        }

        /**
         * 设置设备
         *
         * @param device 设备对象
         */
        public void setDevice(SmartDevice device) {
            if (device == null) {
                throw new IllegalArgumentException("Device cannot be null");
            }
            this.device = device;
        }

        /**
         * 设置目标状态
         *
         * @param desiredState 目标状态
         */
        public void setDesiredState(String desiredState) {
            if (desiredState == null || desiredState.trim().isEmpty()) {
                throw new IllegalArgumentException("Desired state cannot be null or empty");
            }
            this.desiredState = desiredState;
        }

        /**
         * 执行场景动作
         * 将设备设置为目标状态
         */
        public void execute() {
            if (device != null && desiredState != null) {
                device.setState(desiredState);
            }
        }

        /**
         * 返回场景动作的字符串表示
         *
         * @return 格式化的动作信息
         */
        @Override
        public String toString() {
            return "SceneAction{device=" + device + ", desiredState='" + desiredState + "'}";
        }
    }
}