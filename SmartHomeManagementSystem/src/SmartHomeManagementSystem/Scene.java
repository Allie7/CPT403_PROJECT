package SmartHomeManagementSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Scene Class
 * Used to define and store preconfigured combinations of device states
 * Example: A “Movie Night” scene might include dimming living room lights, locking the front door, and other actions
 *
 * Note: Scene is merely a configuration class; actual device control is executed by SmartHomeHub
 *
 */
public class Scene {
    //  Private property
    private String name;
    private String description;
    private Map<String, String> deviceStates; // Equipment Name → Target State Mapping
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

    // ==================== Equipment Status Configuration Management ====================

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

    // ==================== Getter和Setter方法 ====================

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