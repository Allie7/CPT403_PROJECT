package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmartHomeHub {
    protected List<DeviceGroup> groups;
    protected List<SmartDevice> devices;
    protected List<Scene>  scenes;

    protected SmartHomeHub(List<DeviceGroup> groups, List<SmartDevice> devices, List<Scene> scenes)
    {
        this.groups = groups;
        this.devices = devices;
        this.scenes = scenes;
    }

    //
    protected SmartHomeHub() {
        this.devices = new ArrayList<SmartDevice>();
        this.groups = new ArrayList<DeviceGroup>();
        this.scenes = new ArrayList<Scene>();
    }


    protected void addDevice(String name) {
        SmartDevice device = findDeviceByName(name);
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }


    protected void removeDevice(String name) {
        SmartDevice device = findDeviceByName(name);
        if (device != null) {
            devices.remove(device);
        }
    }

    protected SmartDevice findDeviceByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    // 设备组管理方法
    protected void addGroup(String group_name) {
        DeviceGroup group = getGroupByName(group_name);
        if (group != null && !groups.contains(group)) {
            groups.add(group);
            // To do: (may be check if the device in the group is in this.devices, if no then add
            //...
            //...
        }
    }
    protected void removeGroup(String group_name) {
        DeviceGroup group = getGroupByName(group_name);
        if (group != null) {
            groups.remove(group);
        }
    }


    public DeviceGroup getGroupByName(String name) {
        for (DeviceGroup group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    // activating scenes

    /**
     */
    public void addScene(Scene scene) {
        if (scene != null && !scenes.contains(scene)) {
            scenes.add(scene);
        }
    }

    public Scene getSceneByName(String name) {
        for (Scene scene : scenes) {
            if (scene.getName().equals(name)) {
                return scene;
            }
        }
        return null;
    }

    public void executeScene(String name) {
        Scene scene = getSceneByName(name);
        if (scene != null) {
            //scene.execute();

        }
    }

    // Getter 方法（虽然不是类图中显示的，但通常需要）
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(devices); // 返回副本以保护内部数据
    }

    public List<DeviceGroup> getGroups() {
        return new ArrayList<>(groups);
    }

    public List<Scene> getScenes() {
        return new ArrayList<>(scenes);
    }


    protected void viewAllDevices()
    {
        for (SmartDevice device : this.devices) {
            System.out.println( device.toString() );
        }
    }


    protected void controlDevice(String name,String state) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null) {
            currentDevice.setState(state);
        } else {
            throw new IllegalArgumentException("device not found");
        }
    }

    /** Set lighting brightness
     *
     * @param name
     * @param brightness
     */
    protected void controlDevice(String name,Integer brightness) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartLight) {
            currentDevice.setState("ON");
            ((SmartLight) currentDevice).setBrightness(brightness);
        } else {
            throw new IllegalArgumentException("light not found");
        }
    }
    protected void controlDevice(String name,Double temperature) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartThermostat) {
            currentDevice.setState("ON");
            ((SmartThermostat) currentDevice).setTemperature(temperature);
        } else {
            throw new IllegalArgumentException("themostat not found");
        }
    }
    /**
     * **/
    public void manageGroup(String name, String state) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null) {
            currentGroup.applyToAll(state);
        }
    }

    public void manageGroup(String name, Double temperature) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType() == "Light") {
            currentGroup.applyToAll("ON");
            currentGroup.applyToAll(temperature);
        }
    }

    public void manageGroup(String name, Integer brightness) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType() == "Light") {
            currentGroup.applyToAll("ON");
            currentGroup.applyToAll(brightness);
        }
    }
    /*

     */
    public void viewDeviceState(String name) {
        if (name.equals("ALL")) {
            for (SmartDevice currentDevice : devices) {
                System.out.println( "device" + name + " is" +currentDevice.getState());
            }
        }
        else {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null) {
            System.out.printf(name + " is" + currentDevice.getState());

        }
        else {
            DeviceGroup currentGroup = getGroupByName(name);
            if (currentGroup != null) {
                System.out.println(name + " member states:");
                for (SmartDevice device : currentGroup.getDevices()){
                    System.out.printf(name + " is" + device.getState());
                }
            }
        }
        }
    }



}



