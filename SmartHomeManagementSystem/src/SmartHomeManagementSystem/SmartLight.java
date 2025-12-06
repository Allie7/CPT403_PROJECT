package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Lighting Category - Specific Implementation of SmartDevice
 * Inherits from the abstract class SmartDevice, providing lighting-specific functionality
 *
 */
public class SmartLight extends SmartDevice {
    // Luminance Range Constant
    private static final int minBrightness = 0;
    private static final int maxBrightness = 100;

    private int brightness; // Brightness 0-100
    private static ArrayList<String> legalStates = new ArrayList<>(List.of("on","off"));

    /**
     * Constructor
     *
     * @param name Lighting device name
     */
    public SmartLight(String name) {
        super(name, "Light");
        this.brightness = 100; // Default brightness is 100%.
    }

    /**
     * Set Light State
     *
     * @param state The state to set (on/off)
     * @throws IllegalArgumentException If the state is invalid
     */
    @Override
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        String lowerState = state.toLowerCase();
        if (!legalStates.contains(lowerState)) {
            throw new IllegalArgumentException("Invalid state for SmartLight: " + state + ". Must be 'on' or 'off'");
        }
        super.setState(lowerState);
    }

    /**
     * Turn on the lights
     */
    @Override
    public void turnOn() {
        setState("on");
    }

    /**
     * Turn off the lights
     */
    @Override
    public void turnOff() {
        setState("off");
    }

    /**
     * Set light brightness
     * Note: Setting brightness will automatically turn on the lights
     *
     * @param brightness Brightness value (between 0 and 100)
     * @throws IllegalArgumentException If the brightness value is not within the valid range
     */
    public void setBrightness(int brightness) {
        if (brightness < minBrightness || brightness > maxBrightness) {
            throw new IllegalArgumentException(
                    String.format("Invalid brightness: %d. Must be between %d and %d",
                            brightness, minBrightness, maxBrightness)
            );
        }
        this.brightness = brightness;
    }

    /**
     * Get current brightness
     *
     * @return Current brightness value (0-100)
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Return a string representation of the lighting device
     * Contains name, type, status, and brightness information
     *
     * @return Formatted device information string
     */
    @Override
    public String toString() {
        return String.format("SmartLight{name='%s', type='%s', state='%s', Brightness=%d}",
                this.getName(), this.getType(), this.getState(), this.brightness);
    }
}