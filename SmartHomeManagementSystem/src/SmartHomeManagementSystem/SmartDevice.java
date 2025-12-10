package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Device Abstract Base Class
 * All concrete smart device types (such as lights, thermostats, etc.) should inherit from this class.
 *
 * Refactored in v2: Now supports Observer Pattern for notifications.
 * Devices can notify observers when their state changes.
 */
public abstract class SmartDevice {
    // Private Properties - Modified the access modifier from protected to private to comply with the encapsulation principle.
    private String name;
    private String type;
    private DeviceState state;

    // Observer Pattern: List of observers watching this device
    private List<Observer> observers;

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
        this.state = DeviceState.OFF; // The default state is off.
        this.observers = new ArrayList<>();  // Initialize observer list
    }

    // ==================== Observer Pattern Methods ====================

    /**
     * Add an observer to this device
     * @param observer The observer to add
     */
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Remove an observer from this device
     * @param observer The observer to remove
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers about state change
     * @param oldState The previous state
     * @param newState The new state
     */
    protected void notifyObservers(String oldState, String newState) {
        for (Observer observer : observers) {
            observer.update(this, oldState, newState);
        }
    }

    // ==================== Existing Methods ====================

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
     * @return Device status (e.g. "on", "off", "locked", "unlocked")
     */
    public String getState() {
        return this.state == null ? null : this.state.name().toLowerCase();
    }

    /**
     * Set Device State
     * Modified in v2: Now notifies observers when state changes
     *
     * @param state The state to set (on/off/locked/unlocked)
     * @throws IllegalArgumentException If the state is invalid
     */
    public void setState(DeviceState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        String oldState = this.getState();  // Get old state before changing
        this.state = state;
        String newState = this.getState();  // Get new state after changing

        // Notify observers if state actually changed
        if (oldState == null || !oldState.equals(newState)) {
            notifyObservers(oldState, newState);
        }
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
     * @return If the device status is "on", return true; otherwise, return false.
     */
    public boolean isOn() {
        return this.state == DeviceState.ON;
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
                this.name, this.type, this.getState());
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