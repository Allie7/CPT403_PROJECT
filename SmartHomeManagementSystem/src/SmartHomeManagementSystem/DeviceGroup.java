package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Device Group class
 * Used for managing multiple smart devices of the same type, supporting batch operations.
 *
 */
public class DeviceGroup {
    // private fields
    private String name;
    private List<SmartDevice> devices;
    private String type;


    /**
     * Construct method
     * create a device group in which all devices must match the same type
     *
     * @param name device group name
     * @param devices list of devices
     * @throws IllegalArgumentException if the group list is empty or device types does not match
     */
    public DeviceGroup(String name, List<SmartDevice> devices) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be null or empty");
        }
        if (devices == null || devices.isEmpty()) {
            throw new IllegalArgumentException("Device list cannot be null or empty");
        }

        // Verify that all devices are of the same type.
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
     * Get the name of the device group
     *
     * @return Device Group Name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the device group type
     *
     * @return device group type
     */
    public String getType() {
        return type;
    }

    /**
     * Add devices to the device group
     *
     * @param device Devices to be added
     * @throws IllegalArgumentException If the device type does not match or the device is null
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
     * Remove device from device group
     *
     * @param device Devices to be removed
     */
    public void removeDevice(SmartDevice device) {
        if (device != null) {
            devices.remove(device);
        }
    }

    /**
     * Apply the same status to all devices within the group.
     *
     * @param state The state to be applied
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
     * Set the brightness for all lighting equipment within the group.
     *
     * @param brightness Brightness value (0-100)
     * @throws IllegalArgumentException If the device type is not Light or the brightness value is invalid
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
     * Set the temperature for all thermostat devices within the group.
     *
     * @param temperature Temperature value (degrees Celsius)
     * @throws IllegalArgumentException If the device type is not a Thermostat or the temperature value is invalid
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
     * String representation returned to the device group
     *
     * @return A string containing all device information
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
     * Retrieve device list (return a copy to protect internal data)
     *
     * @return Copy of the device list
     */
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(this.devices);
    }

    /**
     * Check whether the device group contains the specified device
     *
     * @param device Equipment to be inspected
     * @return If the device is included, return true.
     */
    public boolean containsDevice(SmartDevice device) {
        return devices.contains(device);
    }

    /**
     * Get the number of devices in a device group
     *
     * @return Number of devices
     */
    public int getDeviceCount() {
        return devices.size();
    }

    /**
     * Clear all devices from the device group
     */
    public void clearDevices() {
        devices.clear();
    }
}