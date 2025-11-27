package SmartHomeManagementSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Scene Configuration Example Class
 * Provides predefined configurations for common scenarios
 * Creates multiple devices
 * Generates scenes using predefined configurations
 * Executes scenes with one click (simultaneously controlling multiple devices)
 * Demonstrates the practical value of scenes
 */
public class SceneConfigExample {

    /**
     * Create “Movie Night” scene configuration
     * - Living room lights: Dim to 20% brightness
     * - Bedroom lights: Turn off
     * - Front door lock: Lock
     * - Thermostat: Set to 22°C
     *
     * @return Device state mapping for Movie Night scene
     */
    public static Map<String, String> getMovieNightConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("Living Room Light", "20");      // Brightness 20%
        config.put("Bedroom light", "off");     // Close
        config.put("Front door lock", "locked");  // locked
        config.put("Thermostat", "22");      // 22°C
        return config;
    }

    /**
     * Create “Away Mode” scene configuration
     * - All lights: Off
     * - All locks: Locked
     * - Thermostat: Set to energy-saving temperature of 18°C
     *
     * @return Device state mapping for Away Mode scene
     */
    public static Map<String, String> getLeavingHomeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("Living Room Light", "off");
        config.put("Bedroom light", "off");
        config.put("Kitchen light", "off");
        config.put("Front door lock", "locked");
        config.put("Back door lock", "locked");
        config.put("Thermostat", "18");
        return config;
    }

    /**
     * Create “Sleep Mode” scene configuration
     * - All lights: Off
     * - All locks: Locked
     * - Thermostat: Set to comfortable sleep temperature of 20°C
     *
     * @return Device state mapping for Sleep Mode scene
     */
    public static Map<String, String> getSleepModeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("Living Room Light", "off");
        config.put("Bedroom light", "off");
        config.put("Front door lock", "locked");
        config.put("Back door lock", "locked");
        config.put("Thermostat", "20");
        return config;
    }

    /**
     * Create “Home Mode” scene configuration
     * - Living room lights: Turn on at 100% brightness
     * - Front door lock: Unlock
     * - Thermostat: Set to comfortable temperature of 23°C
     *
     * @return Device state mapping for Home Mode scene
     */
    public static Map<String, String> getWelcomeHomeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("Living Room Light", "100");
        config.put("Entranceway Light", "on");
        config.put("Front door lock", "unlocked");
        config.put("Thermostat", "23");
        return config;
    }

    /**
     * Create “Party Mode” scene configuration
     * - All lights: Brightest setting
     * - All locks: Unlocked (for guest access)
     * - Thermostat: Set to cool temperature 21°C
     *
     * @return Device state mapping for Party Mode scene
     */
    public static Map<String, String> getPartyModeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("Living Room Light", "100");
        config.put("Kitchen light", "100");
        config.put("Front door lock", "unlocked");
        config.put("Thermostat", "21");
        return config;
    }

    /**
     * Example: How to use these configurations
     */
    public static void main(String[] args) {
        // Create User
        User user = new User("Alice");

        // ==================== Step 1: Create a device and add it to the Hub  ====================
        System.out.println("=== Created scene objects ===");

        // Create Lighting Equipment
        SmartLight livingRoomLight = new SmartLight("Living Room Light");
        SmartLight bedroomLight = new SmartLight("Bedroom light");
        SmartLight kitchenLight = new SmartLight("Kitchen light");
        SmartLight entranceLight = new SmartLight("Entranceway Light");

        //  Create Door Lock Device
        SmartLock frontDoorLock = new SmartLock("Front door lock");
        SmartLock backDoorLock = new SmartLock("Back door lock");

        // Create a thermostat device
        SmartThermostat thermostat = new SmartThermostat("Thermostat");

        // Add device to Hub
        user.addDeviceToHub(livingRoomLight);
        user.addDeviceToHub(bedroomLight);
        user.addDeviceToHub(kitchenLight);
        user.addDeviceToHub(entranceLight);
        user.addDeviceToHub(frontDoorLock);
        user.addDeviceToHub(backDoorLock);
        user.addDeviceToHub(thermostat);

        System.out.println("Device creation complete!\n");

        // ==================== Step 2: Create a scene using a predefined configuration  ====================
        System.out.println("=== Create Scene ===");

        Scene movieNight = user.createScene("Movie Night", getMovieNightConfig());
        System.out.println("Scene ‘Movie Night’ created successfully");

        Scene sleepMode = user.createScene("Sleep Mode", getSleepModeConfig());
        System.out.println("Scenario ‘Sleep Mode’ created successfully");

        Scene welcomeHome = user.createScene("Home Mode", getWelcomeHomeConfig());
        System.out.println("Scene ‘Home Mode’ created successfully\n");

        // ==================== Step 3: Execute the scenario ====================
        System.out.println("=== Execution Scenario: Movie Night ===");
        user.runScene("Movie Night");

        // Check device status
        System.out.println("\n=== Device status after scene execution ===");
        user.viewDeviceState("ALL");

        System.out.println("\nScene demonstration complete!");
    }
}