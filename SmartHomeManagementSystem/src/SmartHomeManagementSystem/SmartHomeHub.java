package SmartHomeManagementSystem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Smart Home Hub Category
 * The core controller of the system, responsible for managing all devices, device groups, and scenes
 *
 */
public class SmartHomeHub {
    private List<DeviceGroup> groups;
    private List<SmartDevice> devices;
    private List<Scene> scenes;

    /**
     * Constructor (with parameters)
     *
     * @param groups  List of device groups
     * @param devices List of devices
     * @param scenes  List of scenes
     */
    protected SmartHomeHub(List<DeviceGroup> groups, List<SmartDevice> devices, List<Scene> scenes) {
        this.groups = groups != null ? new ArrayList<>(groups) : new ArrayList<>();
        this.devices = devices != null ? new ArrayList<>(devices) : new ArrayList<>();
        this.scenes = scenes != null ? new ArrayList<>(scenes) : new ArrayList<>();
    }

    /**
     * Default constructor
     * Creates an empty smart home hub
     */
    protected SmartHomeHub() {
        this.devices = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.scenes = new ArrayList<>();
    }

    // ==================== Equipment Management Method ====================

    /**
     * Add a device to the hub by name
     *
     * @param name Device name
     */
    protected void addDevice(String name) {
        SmartDevice device = findDeviceByName(name);
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }

    /**
     * Add device to hub
     *
     * @param device The device to add
     */
    protected void addDevice(SmartDevice device) {
        if (device != null && !devices.contains(device)) {
            devices.add(device);
        }
    }

    /**
     * Remove device by name
     * Simultaneously remove from all device groups containing this device
     *
     * @param name Device name
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
     * Remove Device
     *
     * @param device The device to be removed
     */
    protected void removeDevice(SmartDevice device) {
        if (device != null) {
            devices.remove(device);
        }
    }

    /**
     * Find device by name
     *
     * @param name Device name
     * @return Found device; returns null if not found
     */
    protected SmartDevice findDeviceByName(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    /**
     * find group through name
     *
     * @param name group name
     * @return the group it finds, if not exists then return null
     */
    public DeviceGroup findGroupByName(String name) {
        for (DeviceGroup group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    // ==================== Equipment Group Management Method ====================

    /**
     * Add device group to hub
     * If devices in the group are not present in the hub, they will be added automatically
     *
     * @param group The device group to add
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
     * Group devices that already exists and add the group to the group list
     *
     * @param name        name of the group
     * @param deviceNames name of the devices
     * @throws IllegalArgumentException when group name exists and device names list are empty or when the device name
     *                                  listed is not in the hub
     **/
    protected void groupDevices(String name, ArrayList<String> deviceNames) {
        if (findGroupByName(name) != null) {
            throw new IllegalArgumentException("Group name exists");
        }
        ArrayList<SmartDevice> devicesGroup = new ArrayList<SmartDevice>();
        if (deviceNames == null) {
            throw new IllegalArgumentException("Device list cannot be null or empty");
        }
        for (String deviceName : deviceNames) {
            SmartDevice device = findDeviceByName(deviceName);
            if (device != null) {
                devices.add(device);
                devicesGroup.add(device);
            } else {
                throw new IllegalArgumentException("Device name " + deviceName + " not found");
            }

        }
        DeviceGroup group = new DeviceGroup(name, devicesGroup);
        groups.add(group);
    }

    public void addDeviceToGroup(String deviceName, String groupName) {
        DeviceGroup group = findGroupByName(groupName);
        SmartDevice device = findDeviceByName(deviceName);
        if (device != null && group != null) {
            group.addDevice(device);
        } else {
            throw new IllegalArgumentException("names not found");
        }
    }

    public void addDeviceToGroup(SmartDevice device, String groupName) {
        DeviceGroup group = findGroupByName(groupName);
        if (group == null) {
            throw new IllegalArgumentException("group not found");
        } else if (device != null && devices.contains(device)) {
            group.addDevice(device);
        } else if (device != null) {
            devices.add(device);
            group.addDevice(device);
        }
    }

    public void addDeviceToGroup(SmartDevice device, DeviceGroup group) {
        if (devices.contains(device) && groups.contains(group)) {
            group.addDevice(device);
        } else {
            throw new IllegalArgumentException("device or group not added");
        }
    }

    public void addDeviceToGroup(String deviceName, DeviceGroup group) {
        SmartDevice device = findDeviceByName(deviceName);
        if (device != null) {
            throw new IllegalArgumentException("device not added");
        }
        if (devices.contains(device) && groups.contains(group)) {
            group.addDevice(device);
        } else {
            throw new IllegalArgumentException("group not added");
        }
    }


    public void removeDeviceFromGroup(String deviceName, String groupName) {
        DeviceGroup group = findGroupByName(groupName);
        SmartDevice device = findDeviceByName(deviceName);
        if (device != null && group != null) {
            group.removeDevice(device);
        } else {
            throw new IllegalArgumentException("names not found");
        }
    }

    public void removeDeviceFromGroup(SmartDevice device, String groupName) {
        DeviceGroup group = findGroupByName(groupName);
        if (group != null) {
            group.removeDevice(device);
        } else if (group == null) {
            throw new IllegalArgumentException("group not found");
        } else {
            throw new IllegalArgumentException("device not legal");
        }
    }

    public void removeDeviceFromGroup(String deviceName, DeviceGroup group) {
        if (!groups.contains(group) | group == null) {
            throw new IllegalArgumentException("group not added");
        }
        SmartDevice device = findDeviceByName(deviceName);
        if (device == null) {
            throw new IllegalArgumentException("device not found");
        } else {
            group.removeDevice(device);
        }
    }

    public void removeDeviceFromGroup(SmartDevice device, DeviceGroup group) {
        if (devices.contains(device) && groups.contains(group)) {
            group.removeDevice(device);
        } else {
            throw new IllegalArgumentException("device or group not added");
        }
    }


    /**
     * Remove a device group by name
     *
     * @param groupName The name of the device group
     */
    protected void removeGroup(String groupName) {
        DeviceGroup group = getGroupByName(groupName);
        if (group != null) {
            groups.remove(group);
        }
    }

    /**
     * Remove Device Group
     *
     * @param group The device group to be removed
     */
    protected void removeGroup(DeviceGroup group) {
        if (group != null) {
            groups.remove(group);
        }
    }

    /**
     * Retrieve a device group by name
     *
     * @param name The device group name
     * @return The found device group. Returns null if it does not exist.
     */
    public DeviceGroup getGroupByName(String name) {
        for (DeviceGroup group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    // ==================== Scene Management Methodology ====================

    /**
     * Add scene to hub
     *
     * @param scene The scene to be added
     */
    public void addScene(Scene scene) {
        if (scene != null && !scenes.contains(scene)) {
            scenes.add(scene);
        }
    }

    /**
     * Get Scene by Name
     *
     * @param name scene name
     * @return Found scene. Returns null if not found.
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
     * Execute the scene with the specified name
     *
     * Refactored in v2: Now delegates execution to the Scene object itself,
     * following the principle that objects should be responsible for their own behavior.
     * This improves cohesion and follows the Command Pattern concept.
     *
     * @param name scene name
     * @throws IllegalArgumentException if the scene is not found
     */
    public void executeScene(String name) {
        Scene scene = getSceneByName(name);
        if (scene != null) {
            // Delegate execution to the Scene itself (Command Pattern)
            scene.execute(this);
        } else {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
    }

    /**
     * Auxiliary Method: Determine if a string is numeric
     *
     * @param str The string to be evaluated
     * @return true if the string is numeric
     */
    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ==================== Getter method ====================

    /**
     * Get all device list (returns a copy)
     *
     * @return A copy of the device list
     */
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(devices);
    }

    /**
     * Get all device group lists (returns a copy)
     *
     * @return A copy of the device group list
     */
    public List<DeviceGroup> getGroups() {
        return new ArrayList<>(groups);
    }

    /**
     * Get all scene lists (returns a copy)
     *
     * @return A copy of the scene list
     */
    public List<Scene> getScenes() {
        return new ArrayList<>(scenes);
    }

    // ==================== View Method ====================

    /**
     * Display information for all devices
     */
    protected void viewAllDevices() {
        for (SmartDevice device : devices) {
            System.out.println(device.toString());
        }
    }

    /**
     * Display information for all device groups
     */
    protected void viewAllGroups() {
        for (DeviceGroup group : groups) {
            System.out.println("Group " + group.getName() + " : " + group.toString());
        }
    }

    // ==================== Equipment Control Method ====================

    /**
     * Control the state of a single device
     *
     * @param name Device name
     * @param state Target state
     * @throws IllegalArgumentException If the device does not exist
     */
    protected void controlDevice(String name,DeviceState state) {

        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null) {
            currentDevice.setState(state);
        } else {
            throw new IllegalArgumentException("Device " + name + " not found");
        }
    }

    /**
     * Set the brightness of a lighting device
     *
     * @param name Device name
     * @param brightness Brightness value (0-100)
     * @throws IllegalArgumentException If the device does not exist or is not a lighting device
     */
    protected void controlBrightness(String name, int brightness) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartLight) {
            currentDevice.turnOn();
            ((SmartLight) currentDevice).setBrightness(brightness);
        } else {
            throw new IllegalArgumentException("Light " + name + " not found");
        }
    }

    /**
     * Set the temperature of the thermostat device
     *
     * @param name Device name
     * @param temperature Target temperature
     * @throws IllegalArgumentException If the device does not exist or is not a thermostat device
     */
    protected void controlTemperature(String name, Double temperature) {
        SmartDevice currentDevice = findDeviceByName(name);
        if (currentDevice != null && currentDevice instanceof SmartThermostat) {
            currentDevice.turnOn();
            ((SmartThermostat) currentDevice).setTemperature(temperature);
        } else {
            throw new IllegalArgumentException("Thermostat " + name + " not found");
        }
    }

    // ==================== Equipment Group Control Method ====================

    /**
     * Manage Device Group State
     *
     * @param name Device group name
     * @param state Target state
     * @throws IllegalArgumentException If the device group does not exist
     */
    public void manageGroup(String name, DeviceState state) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null) {
            currentGroup.applyToAll(state);
        } else {
            throw new IllegalArgumentException("Group " + name + " not found");
        }
    }

    /**
     * Set the temperature for the thermostat device group
     *
     * @param name Device group name
     * @param temperature Target temperature
     * @throws IllegalArgumentException If the device group does not exist or the type does not match
     */
    public void manageGroupTemperature(String name, Double temperature) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType().equals("Thermostat")) {
            currentGroup.applyToAll(DeviceState.ON);
            currentGroup.applyToAll(temperature);
        } else {
            throw new IllegalArgumentException("Thermostat group " + name + " not found");
        }
    }

    /**
     * Set the brightness of a lighting device group
     *
     * @param name Device group name
     * @param brightness Brightness value (0-100)
     * @throws IllegalArgumentException If the device group does not exist or the type does not match
     */
    public void manageGroupBrightness(String name, int brightness) {
        DeviceGroup currentGroup = getGroupByName(name);
        if (currentGroup != null && currentGroup.getType().equals("Light")) {
            currentGroup.applyToAll(DeviceState.ON);
            currentGroup.applyToAll(brightness);
        } else {
            throw new IllegalArgumentException("Light group " + name + " not found");
        }
    }

    // ==================== Methods for Checking Device Status ====================

    /**
     * View detailed status of a single device
     *
     * @param currentDevice The device to view
     * @throws IllegalArgumentException If the device does not exist
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
     * View Device Status
     * Supports viewing individual devices, device groups, or all devices (using “ALL”)
     *
     * @param name Device name, device group name, or “ALL”
     * @throws IllegalArgumentException If the specified name does not exist
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