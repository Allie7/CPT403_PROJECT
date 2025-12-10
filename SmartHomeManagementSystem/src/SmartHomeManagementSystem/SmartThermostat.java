package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Thermostat Category - Specific Implementation of SmartDevice
 * Inherits from the abstract class SmartDevice, providing temperature control functionality
 *
 */
public class SmartThermostat extends SmartDevice {
    // Temperature Range Constant (°C)
    private static final double minTemperature = 0.0;
    private static final double maxTemperature = 40.0;

    private double temperature;

    /**
     * Constructor
     *
     * @param name Thermostat device name
     */
    public SmartThermostat(String name) {
        super(name, "Thermostat");
        this.temperature = 22.0; // Default temperature: 22°C
    }

    /**
     * Set thermostat state
     *
     * @param state State to set (on/off)
     * @throws IllegalArgumentException If state is invalid
     */
    @Override
    public void setState(DeviceState state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        //String lowerState = state.toLowerCase();
        //if (!legalStates.contains(lowerState)) {
        //    throw new IllegalArgumentException("Invalid state for SmartThermostat: " + state + ". Must be 'on' or 'off'");
        //}
        super.setState(state);
    }

    /**
     * Turn on the thermostat
     */
    @Override
    public void turnOn() {
        setState(DeviceState.ON);
    }

    /**
     * Turn off the thermostat
     */
    @Override
    public void turnOff() {
        setState(DeviceState.OFF);
    }

    /**
     * Set Target Temperature
     * Note: Setting the temperature will automatically activate the thermostat
     *
     * @param temperature Target temperature (between 0°C and 40°C)
     * @throws IllegalArgumentException If the temperature value is outside the valid range
     */
    public void setTemperature(double temperature) {
        if (temperature < minTemperature || temperature > maxTemperature) {
            throw new IllegalArgumentException(
                    String.format("Invalid temperature: %.1f°C. Must be between %.1f°C and %.1f°C",
                            temperature, minTemperature, maxTemperature)
            );
        }
        this.temperature = temperature;
    }

    /**
     * Get the current target temperature
     *
     * @return The current target temperature (in degrees Celsius)
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * Return a string representation of the thermostat device
     * Contains name, type, status, and temperature information
     *
     * @return Formatted device information string
     */
    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s', Temperature=%.1f}",
                this.getName(), this.getType(), this.getState(), this.temperature);
    }
}