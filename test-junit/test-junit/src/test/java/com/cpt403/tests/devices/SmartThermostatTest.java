package com.cpt403.tests.devices;

import SmartHomeManagementSystem.SmartThermostat;
import SmartHomeManagementSystem.SmartDevice;
import com.cpt403.tests.base.BaseDeviceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SmartThermostat test class demonstrating OOP principles:
 * Inheritance, Encapsulation, and Polymorphism
 */
@DisplayName("SmartThermostat Device Tests")
public class SmartThermostatTest extends BaseDeviceTest {
    
    private SmartThermostat thermostat;
    private static final double DELTA = 0.001; // Floating point comparison precision
    
    /**
     * Factory method - creates SmartThermostat instance
     */
    @Override
    protected SmartThermostat createDevice() {
        return new SmartThermostat(testDeviceName);
    }
    
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        thermostat = (SmartThermostat) device;
    }
    
    @Test
    @DisplayName("Test SmartThermostat default state is off")
    public void testDefaultState() {
        assertEquals("off", thermostat.getState(), "Thermostat should default to off");
        assertFalse(thermostat.isOn(), "Thermostat should be off by default");
    }
    
    @Test
    @DisplayName("Test SmartThermostat default temperature is 22°C")
    public void testDefaultTemperature() {
        assertEquals(22.0, thermostat.getTemperature(), DELTA, 
            "Default temperature should be 22.0°C");
    }
    
    @Test
    @DisplayName("Test SmartThermostat turn on/off functionality")
    public void testTurnOnOff() {
        thermostat.turnOn();
        assertEquals("on", thermostat.getState(), "Thermostat should be on after turnOn()");
        assertTrue(thermostat.isOn(), "isOn() should return true when thermostat is on");
        
        thermostat.turnOff();
        assertEquals("off", thermostat.getState(), "Thermostat should be off after turnOff()");
        assertFalse(thermostat.isOn(), "isOn() should return false when thermostat is off");
    }
    
    @ParameterizedTest(name = "Test valid temperature value: {0}°C")
    @ValueSource(doubles = {0.0, 20.0, 22.5, 40.0})
    @DisplayName("Test valid temperature range boundary values")
    public void testValidTemperatureRange(double temperature) {
        thermostat.turnOn();
        thermostat.setTemperature(temperature);
        assertEquals(temperature, thermostat.getTemperature(), DELTA,
            "Temperature " + temperature + "°C should be valid");
    }
    
    @ParameterizedTest(name = "Test invalid temperature value: {0}°C")
    @ValueSource(doubles = {-1.0, -5.0, 40.1, 50.0, 100.0})
    @DisplayName("Test invalid temperature values should throw exception")
    public void testInvalidTemperatureThrowsException(double invalidTemperature) {
        thermostat.turnOn();
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> thermostat.setTemperature(invalidTemperature),
            "Setting temperature to " + invalidTemperature + "°C should throw IllegalArgumentException"
        );
        
        assertNotNull(exception.getMessage(), "Exception message should not be null");
    }
    
    @Test
    @DisplayName("Test set temperature functionality")
    public void testSetTemperatureTurnsOnThermostat() {
        assertEquals("off", thermostat.getState());
        
        // Note: setTemperature() does not automatically turn on the thermostat
        // Need to turn on first, then set temperature
        thermostat.turnOn();
        thermostat.setTemperature(25.0);
        assertEquals("on", thermostat.getState(), 
            "Setting temperature should turn thermostat on");
        assertEquals(25.0, thermostat.getTemperature(), DELTA, 
            "Temperature should be set correctly");
    }
    
    @ParameterizedTest(name = "Transition from temperature {0}°C to {1}°C")
    @CsvSource({
        "0.0, 40.0",
        "40.0, 0.0",
        "20.0, 25.5",
        "22.0, 22.0"
    })
    @DisplayName("Test temperature value transitions")
    public void testTemperatureTransition(double from, double to) {
        thermostat.turnOn();
        thermostat.setTemperature(from);
        assertEquals(from, thermostat.getTemperature(), DELTA);
        
        thermostat.setTemperature(to);
        assertEquals(to, thermostat.getTemperature(), DELTA,
            "Temperature should transition from " + from + "°C to " + to + "°C");
    }
    
    @Test
    @DisplayName("Test device basic information")
    public void testDeviceInfo() {
        testBasicDeviceOperations();
        
        assertEquals("Thermostat", thermostat.getType(), 
            "Device type should be 'Thermostat'");
        assertNotNull(thermostat.getName(), "Device name should not be null");
    }
    
    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        thermostat.turnOn();
        thermostat.setTemperature(23.5);
        
        String toString = thermostat.toString();
        assertNotNull(toString, "toString() should not return null");
        assertTrue(toString.contains(thermostat.getName()), 
            "toString should contain device name");
        assertTrue(toString.contains("Thermostat"), 
            "toString should contain device type");
    }
}





