package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Device Abstract Base Class
 * All concrete smart device types (such as lights, thermostats, etc.) should inherit from this class.
 *
 */
public abstract class SmartDevice {
    // Private Properties - Modified the access modifier from protected to private to comply with the encapsulation principle.
    private String name;
    private String type;
    private String state;

    // List of Valid States (Subclasses may override)
    public static String[] legalStates = {"on","off"};

    /**
     * Constructor
     *
     * @param name Device name (unique identifier)
     * @param type Device type (e.g., Light, Thermostat, Lock)
     */
    public SmartDevice(String name, String type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Device name cannot be null or empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Device type cannot be null or empty");
        }
        this.name = name;
        this.type = type;
        this.state = "off"; // The default state is off.
    }

    /**
     * Get Device Name
     *
     * @return Device name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get device type
     *
     * @return Device type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the device's current status
     *
     * @return Device status
     */
    public String getState() {
        return this.state;
    }

    /**
     * Set Device State
     * Note: This method validates the state's legitimacy
     *
     * @param state The state to set
     * @throws IllegalArgumentException If the state is invalid
     */
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        // State validation is implemented by subclasses (since each device has distinct valid states).
        this.state = state.toLowerCase();
    }

    /**
     * Open Device (abstract method, with concrete logic implemented by subclasses)
     */
    public abstract void turnOn();

    /**
     * Shut down the device (abstract method; concrete implementation provided by subclasses)
     */
    public abstract void turnOff();

    /**
     * Check whether the equipment is turned on.
     *
     * @return If the device status is “on”, return true; otherwise, return false.
     */
    public boolean isOn() {
        return "on".equalsIgnoreCase(state);
    }

    /**
     * Switch device status (on/off)
     */
    public void toggle() {
        if (isOn()) {
            turnOff();
        } else {
            turnOn();
        }
    }

    /**
     * Return a string representation of the device
     *
     * @return A formatted string containing the device name, type, and status
     */
    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s'}",
                this.name, this.type, this.state);
    }

    /**
     * Determine if two devices are equal
     * Based on device name and type
     *
     * @param obj The object to compare
     * @return true if the device name and type are identical
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SmartDevice that = (SmartDevice) obj;
        return name.equals(that.name) && type.equals(that.type);
    }
}