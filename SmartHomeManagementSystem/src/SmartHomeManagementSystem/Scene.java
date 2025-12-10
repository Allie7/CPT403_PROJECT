package SmartHomeManagementSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Scene Class
 * Used to define and store preconfigured combinations of device states
 * Example: A "Movie Night" scene might include dimming living room lights, locking the front door, and other actions
 *
 * Refactored in v2: Scene now follows the Command Pattern concept,
 * capable of executing itself rather than relying on SmartHomeHub for execution.
 */
public class Scene {
    // Private properties
    private String name;
    private String description;
    private Map<String, String> deviceStates; // Device Name → Target State Mapping
    protected String state; // Scene state (e.g., active, inactive, etc.)

    /**
     * Constructor
     * Create a scene via device state mapping
     *
     * @param name scene name
     * @param deviceStates mapping from device names to target states
     */
    public Scene(String name, Map<String, String> deviceStates) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene name cannot be null or empty");
        }
        this.name = name;
        this.description = "";
        this.state = "inactive"; // The default scene state is inactive.

        // Storage Device Status Configuration (Deep Copy for Data Protection)
        if (deviceStates != null) {
            this.deviceStates = new HashMap<>(deviceStates);
        } else {
            this.deviceStates = new HashMap<>();
        }
    }

    // ==================== Core Execution Method (NEW in v2) ====================

    /**
     * Execute the scene - applies all configured device states
     *
     * This method follows the Command Pattern concept: the Scene object
     * encapsulates all the information needed to perform an action,
     * and can execute itself when triggered.
     *
     * @param hub The SmartHomeHub that contains the devices to control
     */
    public void execute(SmartHomeHub hub) {
        if (hub == null) {
            throw new IllegalArgumentException("SmartHomeHub cannot be null");
        }

        // Mark the scene as active
        this.state = "active";
        System.out.println("Executing scene: " + this.name);

        // Iterate through all device configurations in the scene
        for (Map.Entry<String, String> entry : deviceStates.entrySet()) {
            String deviceName = entry.getKey();
            String targetValue = entry.getValue();

            // Find devices through the Hub
            SmartDevice device = hub.findDeviceByName(deviceName);
            if (device != null) {
                applyStateToDevice(device, targetValue);
            } else {
                // Device does not exist. Log warning.
                System.err.println("Device not found in scene '" + name + "': " + deviceName);
            }
        }

        System.out.println("Scene '" + this.name + "' execution completed.");
    }

    /**
     * Apply the target state/value to a specific device
     * Handles different device types appropriately
     *
     * @param device The device to control
     * @param targetValue The target state or value
     */
    private void applyStateToDevice(SmartDevice device, String targetValue) {
        try {
            if (device instanceof SmartLight) {
                applyToLight((SmartLight) device, targetValue);
            } else if (device instanceof SmartThermostat) {
                applyToThermostat((SmartThermostat) device, targetValue);
            } else if (device instanceof SmartLock) {
                applyToLock((SmartLock) device, targetValue);
            } else {
                // Generic device: try to set state directly
                applyGenericState(device, targetValue);
            }
        } catch (RuntimeException e) {
            System.err.println("Failed to set " + device.getName() + " to " + targetValue + ": " + e.getMessage());
        }
    }

    /**
     * Apply state to a SmartLight device
     */
    private void applyToLight(SmartLight light, String targetValue) {
        if (isNumeric(targetValue)) {
            // Numeric value: treat as brightness
            int brightness = Integer.parseInt(targetValue);
            light.turnOn();
            light.setBrightness(brightness);
        } else if (targetValue.equalsIgnoreCase("on")) {
            light.turnOn();
        } else if (targetValue.equalsIgnoreCase("off")) {
            light.turnOff();
        } else {
            throw new IllegalArgumentException("Invalid state for light: " + targetValue);
        }
    }

    /**
     * Apply state to a SmartThermostat device
     */
    private void applyToThermostat(SmartThermostat thermostat, String targetValue) {
        if (isNumeric(targetValue)) {
            // Numeric value: treat as temperature
            double temperature = Double.parseDouble(targetValue);
            thermostat.turnOn();
            thermostat.setTemperature(temperature);
        } else if (targetValue.equalsIgnoreCase("on")) {
            thermostat.turnOn();
        } else if (targetValue.equalsIgnoreCase("off")) {
            thermostat.turnOff();
        } else {
            throw new IllegalArgumentException("Invalid state for thermostat: " + targetValue);
        }
    }

    /**
     * Apply state to a SmartLock device
     */
    private void applyToLock(SmartLock lock, String targetValue) {
        if (targetValue.equalsIgnoreCase("locked") || targetValue.equalsIgnoreCase("lock")) {
            lock.lock();
        } else if (targetValue.equalsIgnoreCase("unlocked") || targetValue.equalsIgnoreCase("unlock")) {
            lock.unlock();
        } else {
            throw new IllegalArgumentException("Invalid state for lock: " + targetValue);
        }
    }

    /**
     * Apply generic state to a device (fallback method)
     */
    private void applyGenericState(SmartDevice device, String targetValue) {
        if (targetValue.equalsIgnoreCase("on")) {
            device.turnOn();
        } else if (targetValue.equalsIgnoreCase("off")) {
            device.turnOff();
        } else {
            throw new IllegalArgumentException("Unknown state: " + targetValue);
        }
    }

    /**
     * Helper method: Check if a string is numeric
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

    // ==================== Device State Configuration Management ====================

    /**
     * Add or update device state configuration
     *
     * @param deviceName Device name
     * @param targetState Target state
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
     * Remove device status configuration
     *
     * @param deviceName Device name
     */
    public void removeDeviceState(String deviceName) {
        if (deviceName != null) {
            deviceStates.remove(deviceName);
        }
    }

    /**
     * Retrieve the target state of a specified device
     *
     * @param deviceName Device name
     * @return Target state. Returns null if the device is not present in the scene.
     */
    public String getDeviceState(String deviceName) {
        return deviceStates.get(deviceName);
    }

    /**
     * Retrieve all device state configurations (returns a copy to protect internal data)
     *
     * @return A copy of the device state map
     */
    public Map<String, String> getDeviceStates() {
        return new HashMap<>(deviceStates);
    }

    /**
     * Check if the scene contains the specified device
     *
     * @param deviceName Device name
     * @return Returns true if the device is present
     */
    public boolean containsDevice(String deviceName) {
        return deviceStates.containsKey(deviceName);
    }

    /**
     * Get the number of devices in the scene
     *
     * @return Number of devices
     */
    public int getDeviceCount() {
        return deviceStates.size();
    }

    /**
     * Clear all device configurations in the scene
     */
    public void clearDeviceStates() {
        deviceStates.clear();
    }

    // ==================== Getter and Setter Methods ====================

    /**
     * Get Scene Name
     *
     * @return scene name
     */
    public String getName() {
        return name;
    }

    /**
     * Set scene name
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
     * Get Scene Description
     *
     * @return Scene description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set Scene Description
     *
     * @param description Scene description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get scene state
     *
     * @return Scene state (e.g., active, inactive)
     */
    public String getState() {
        return state;
    }

    /**
     * Set Scene State
     *
     * @param state Scene state
     */
    public void setState(String state) {
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene state cannot be null or empty");
        }
        this.state = state;
    }

    /**
     * String representation of the scene
     *
     * @return Formatted string containing the scene name, status, and device configuration
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
