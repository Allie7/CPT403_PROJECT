package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User Class
 * Represents a user in the smart home system, providing an interface
 * for interaction with the SmartHomeHub.
 *
 */
public class User {
    private String username;
    private SmartHomeHub hub;

    /**
     * Construct method
     * construct a user object using its username and the hub it uses.
     *
     * @param username username
     * @param hub the hub it uses
     */
    public User(String username, SmartHomeHub hub) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
        this.hub = hub != null ? hub : new SmartHomeHub();
    }

    /**
     * Construct a user who does not already have a hub
     *
     * @param username username
     */
    public User(String username) {
        this(username, new SmartHomeHub());
    }

    /**
     * default user construct
     */
    public User() {
        this.username = "DefaultUser";
        this.hub = new SmartHomeHub();
    }

    // ==================== View methods ====================

    /**
     * view all devices
     */
    public void viewAllDevices() {
        hub.viewAllDevices();
    }

    /**
     * View All Device Groups
     */
    public void viewAllGroups() {
        hub.viewAllGroups();
    }

    /**
     * view a particular device or a particular devices of a particular group's state
     * by calling the group/device's name
     * @param deviceName the device name, or "all"
     */
    public void viewDeviceState(String deviceName) {
        hub.viewDeviceState(deviceName);
    }

    /**
     * view a particular device's state by "pointing at" the device
     *
     * @param currentDevice the device to see
     */
    public void viewDeviceState(SmartDevice currentDevice) {
        hub.viewSingleDevice(currentDevice);
    }

    /**
     * see the member of a device group's states by "pointing at" the group
     *
     * @param group the group to see
     */
    public void viewDeviceState(DeviceGroup group) {
        hub.viewDeviceState(group.getName());
    }

    // ==================== control methods through hub） ====================

    /**
     * control a particular device through the hub by calling its name
     *
     * @param name the device's name
     * @param state the target state
     */
    public void controlDevices(String name, String state) {
        hub.controlDevice(name, state);
    }

    /**
     * control(through the hub) a particular lighting device, turn it on and set it to desired
     * brightness by calling its name.
     *
     * @param name device name
     * @param num brightness（0-100）
     */
    public void controlDevices(String name, int num) {
        hub.controlDevice(name, num);
    }

    /**
     * control(through the hub) a particular thermostat device, turn it on and set it to desired
     * temperature by calling its name.
     * @param name device name
     * @param num desired temperature
     */
    public void controlDevices(String name, double num) {
        hub.controlDevice(name, num);
    }

    // ==================== control shortcuts（through the Hub） ====================

    /**
     * turn on the device through the name
     *
     * @param deviceName device name
     */
    public void turnOnDevice(String deviceName) {
        hub.controlDevice(deviceName, "on");
    }

    /**
     * turn off the device through the name
     *
     * @param deviceName device name
     */
    public void turnOffDevice(String deviceName) {
        hub.controlDevice(deviceName, "off");
    }

    /**
     * lock the device through the name
     *
     * @param deviceName device name
     */
    public void lockDevice(String deviceName) {
        hub.controlDevice(deviceName, "locked");
    }

    /**
     * unlock the device through the name
     *
     * @param deviceName device name
     */
    public void unlockDevice(String deviceName) {
        hub.controlDevice(deviceName, "unlocked");
    }

    // ==================== direct controls（not through Hub） ====================

    /**
     * turn on the device directly (not through the hub)
     *
     * @param device the device object
     */
    public void turnOnDevice(SmartDevice device) {
        device.turnOn();
    }

    /**
     * turn off the device directly (not through the hub)
     *
     * @param device the device object
     */
    public void turnOffDevice(SmartDevice device) {
        device.turnOff();
    }

    /**
     *  lock the device directly (not through the hub)
     *
     * @param device the device object
     * @throws IllegalArgumentException if the device is not SmartLock
     */
    public void lockDevice(SmartDevice device) {
        if (device instanceof SmartLock) {
            ((SmartLock) device).lock();
        } else {
            throw new IllegalArgumentException(device.getName() + " is not a SmartLock");
        }
    }

    /**
     *  unlock the device directly (not through the hub)
     *
     * @param device the device object
     * @throws IllegalArgumentException if the device is not a SmartLock
     */
    public void unlockDevice(SmartDevice device) {
        if (device instanceof SmartLock) {
            ((SmartLock) device).unlock();
        } else {
            throw new IllegalArgumentException(device.getName() + " is not a SmartLock");
        }
    }

    // ==================== Managing the Hub ====================

    /**
     * add an empty device (only a name ) to the hub. Often useful to the engineers when developing and debugging
     * not really useful for the user, actually.
     *
     * @param deviceName the name of the device
     */
    public void addDeviceToHub(String deviceName) {
        hub.addDevice(deviceName);
    }

    /**
     * add a device to the hub.
     *
     * @param device the device object
     */
    public void addDeviceToHub(SmartDevice device) {
        hub.addDevice(device);
    }

    /**
     * remove a device from a hub through its name
     *
     * @param deviceName the device's name
     */
    public void removeDeviceFromHub(String deviceName) {
        hub.removeDevice(deviceName);
    }

    /**
     * remove a device from a hub
     *
     * @param device the device object
     */
    public void removeDeviceFromHub(SmartDevice device) {
        hub.removeDevice(device);
    }


    // ==================== Managing Device groups through the hub ====================

    /**
     * create device groups directly with a list of devices. Mimicking real-world use case in which the user
     * groups some devices directly and input them into the smart hub
     * All devices must match the same type
     *
     * @param name group name
     * @param devices devices list
     * @throws IllegalArgumentException if member devices' types do not match
     */
    public void createGroup(String name, ArrayList<SmartDevice> devices) {
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
     * create device groups using list of devices that already in smartHomeHub's devices list.
     * Mimicking real-world use case in which the user input devices names to the smartHomeHub to tell
     * the hub to create a device group
     *
     * @param name group name
     * @param deviceNames list of deviceNames
     */
    public void groupDevicesInHub(String name, ArrayList<String> deviceNames) {

        hub.groupDevices(name, deviceNames);
    }


    /**
     * add a device to a group in hub through names
     *
     * @param deviceName the name of the device
     * @param group the name of the group
     */
    public void addMemberToGroup(String deviceName,String group_name) {
        hub.addDeviceToGroup(deviceName, group_name);
    }

    /**
     * add a device to a group in hub through device name
     *
     * @param device the device
     * @param group_name the name of the group
     */
    public void addMemberToGroup(SmartDevice device,String group_name) {
        hub.addDeviceToGroup(device, group_name);
    }


    /**
     * add a device to a group in hub through device name
     *
     * @param device the device
     * @param group the group
     */
    public void addMemberToGroup(SmartDevice device, DeviceGroup group) {
        hub.addDeviceToGroup(device, group);
    }


    /**
     * remove a device to a group in hub through names
     *
     * @param deviceName the name of the device
     * @param group_name the name of the group
     */
    public void removeMemberFromGroup(String deviceName,String group_name) {
        hub.removeDeviceFromGroup(deviceName, group_name);
    }

    /**
     * remove a device to a group in hub through device name
     *
     * @param device the device
     * @param group_name the name of the group
     */
    public void removeMemberFromGroup(SmartDevice device,String group_name) {
        hub.removeDeviceFromGroup(device, group_name);
    }


    /**
     * remove a device to a group in hub through device name
     *
     * @param device the device
     * @param group the group
     */
    public void removeMemberFromGroup(SmartDevice device, DeviceGroup group) {
        hub.removeDeviceFromGroup(device, group);
    }




    /**
     * input a name of the group to ask the hub to delete it
     *
     * @param group_name device group name
     */
    public void removeGroupFromHub(String group_name) {
        hub.removeGroup(group_name);
    }

    /**
     * remove a device group from the hub directly.
     *
     * @param group device group object
     */
    public void removeGroupFromHub(DeviceGroup group) {
        hub.removeGroup(group);
    }

    // ==================== Control Device Groups through the Hub ====================

    /**
     * set the group to desired state
     *
     * @param name name of the group
     * @param state target state
     */
    public void manageGroup(String name, String state) {
        hub.manageGroup(name, state);
    }

    /**
     * set the group of smart light to desired brightness and turn them on.
     *
     * @param name group name
     * @param brightness target brightness(0-100)
     */
    public void manageGroup(String name, int brightness) {
        hub.manageGroup(name, brightness);
    }

    /**
     * set the group of smart thermostat to desired  and turn them on.
     *
     * @param name name of the device
     * @param temperature target temperature
     */
    public void manageGroup(String name, double temperature) {
        hub.manageGroup(name, temperature);
    }

    // ==================== Device Group Control ShortCuts ====================

    /**
     * turn on all members of the device group
     *
     * @param group_name group name
     */
    public void turnOnGroup(String group_name) {
        hub.manageGroup(group_name, "on");
    }

    /**
     * turn off all members of the device group
     *
     * @param group_name group name
     */
    public void turnOffGroup(String group_name) {
        hub.manageGroup(group_name, "off");
    }

    /**
     * lock all members of the device group
     *
     * @param group_name group name
     */
    public void lockGroup(String group_name) {
        hub.manageGroup(group_name, "locked");
    }

    /**
     * unlock  all members of the device group
     *
     * @param group_name group name
     */
    public void unlockGroup(String group_name) {
        hub.manageGroup(group_name, "unlocked");
    }

    // ==================== Managing Scenes ====================

    /**
     * create scenes
     *
     * @param name Scene names
     * @param deviceStates config map of the devices and target states
     * @return Created scene objects
     */
    public Scene createScene(String name, Map<String, String> deviceStates) {
        // create scenes through the config map
        Scene newScene = new Scene(name, deviceStates);

        // add scene to hub
        hub.addScene(newScene);

        return newScene;
    }

    /**
     * modify scene
     * updating the scene devices and states
     *
     * @param name scene name
     * @param deviceStates config map of devices and target states
     */
    public void modifyScene(String name, Map<String, String> deviceStates) {
        Scene scene = hub.getSceneByName(name);
        if (scene != null) {
            // clear all the scene settings
            scene.clearDeviceStates();

            // add new settings
            if (deviceStates != null) {
                for (Map.Entry<String, String> entry : deviceStates.entrySet()) {
                    scene.addDeviceState(entry.getKey(), entry.getValue());
                }
            }
        } else {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
    }

    /**
     * add new device-target state pair to the scene
     *
     * @param sceneName scene name
     * @param deviceName device name
     * @param targetState target state
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
     * remove device from the scene
     *
     * @param sceneName scene name
     * @param deviceName device name
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
     * execute scene
     *
     * @param name scene name
     */
    public void runScene(String name) {
        hub.executeScene(name);
    }
}