package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// Scene 类实现
public class Scene {
    // 私有属性
    private String name;
    private String description;
    private List<SceneAction> actions;
    protected String state;

    // 构造方法
    public Scene(String name, Map<String,String> device_states) {
        this.name = name;
        //this.description = "";
        /** build Scene by device_states map
         **
         */

        this.actions = new ArrayList<>();

    }

    // 公有方法
    public void addAction(SceneAction action) {
        if (action != null && !actions.contains(action)) {
            actions.add(action);
        }
    }

    public void removeAction(SceneAction action) {
        if (action != null) {
            actions.remove(action);
        }
    }

    public void execute() {
        // 执行所有场景动作
        for (SceneAction action : actions) {
            action.execute();
        }
    }

    // Getter 和 Setter 方法（可选，根据实际需求添加）
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SceneAction> getActions() {
        return new ArrayList<>(actions); // 返回副本以保护内部数据
    }

    // SceneAction class implementation
    public class SceneAction {
        // Private attributes as shown in UML
        private SmartDevice device;
        private String desiredState;

        // Constructor
        public SceneAction(SmartDevice device, String desiredState) {
            this.device = device;
            this.desiredState = desiredState;
        }

        // Getter methods as shown in UML
        public SmartDevice getDevice() {
            return device;
        }

        public String getDesiredState() {
            return desiredState;
        }

        // Setter methods (not shown in UML but typically needed)
        public void setDevice(SmartDevice device) {
            this.device = device;
        }

        public void setDesiredState(String desiredState) {
            this.desiredState = desiredState;
        }

        // Additional useful method
        public void execute() {
            if (device != null && desiredState != null) {
                device.setState(desiredState);
            }
        }

        @Override
        public String toString() {
            return "SceneAction{device=" + device + ", desiredState='" + desiredState + "'}";
        }
    }


}