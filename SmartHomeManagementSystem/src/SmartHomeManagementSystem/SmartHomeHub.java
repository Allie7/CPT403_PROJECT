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

    protected void addDevice(SmartDevice device) {
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

    protected void removeDevice(SmartDevice device) {
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

    //
    protected void addGroup(DeviceGroup group) {

        if (group != null && !groups.contains(group)) {

            for (SmartDevice device : group.devices) {
                if (!devices.contains(device)) {
                    devices.add(device);
                }
            }
            groups.add(group);
        }
    }
    protected void removeGroup(String group_name) {
        DeviceGroup group = getGroupByName(group_name);
        if (group != null) {
            groups.remove(group);
        }
    }

    protected void removeGroup(DeviceGroup group) {
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
        for (SmartDevice device : devices) {
            System.out.println( device.toString() );
        }
    }


    protected void controlDevice(String name,String state) {
        state = state.toLowerCase();
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
            currentDevice.turnOn();
            ((SmartLight) currentDevice).setBrightness(brightness);
        } else {
            throw new IllegalArgumentException("light not found");
        }
    }
    protected void controlDevice(String name,Double temperature) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartThermostat) {
            currentDevice.turnOn();
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
    public void viewSingleDevice(SmartDevice currentDevice) {
        if (currentDevice != null) {
            System.out.printf(currentDevice.getName() + " is " + currentDevice.getState());
            if (currentDevice instanceof SmartLight) {
                System.out.println(" and current brightness is " + ((SmartLight) currentDevice).getBrightness());
            } else if (currentDevice instanceof SmartThermostat) {
                System.out.println(" and current temperature is " + ((SmartThermostat) currentDevice).getTemperature());
            } else{
                System.out.println("");
            }
        } else {
            throw new IllegalArgumentException("device not found");
        }
    }

    /*

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
                    for (SmartDevice currentDevice : currentGroup.devices) {

                        viewSingleDevice(currentDevice);
                    }
                }
            } else {
                throw new IllegalArgumentException("name not found");
            }
        }
    }


}



