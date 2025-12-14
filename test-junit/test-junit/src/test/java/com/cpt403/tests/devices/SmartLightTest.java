package com.cpt403.tests.devices;

import SmartHomeManagementSystem.SmartLight;
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
 * SmartLight test class demonstrating OOP principles:
 * Inheritance, Encapsulation, and Polymorphism
 */
@DisplayName("SmartLight Device Tests")
public class SmartLightTest extends BaseDeviceTest {
    
    private SmartLight light;
    
    /**
     * Factory method - creates SmartLight instance
     */
    @Override
    protected SmartLight createDevice() {
        return new SmartLight(testDeviceName);
    }
    
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        light = (SmartLight) device;
    }
    
    @Test
    @DisplayName("Test SmartLight default state is off")
    public void testDefaultState() {
        assertEquals("off", light.getState(), "Lights should default to off");
        assertFalse(light.isOn(), "Light should be off by default");
    }
    
    @Test
    @DisplayName("Test SmartLight turn on/off functionality")
    public void testTurnOnOff() {
        light.turnOn();
        assertEquals("on", light.getState(), "Light should be on after turnOn()");
        assertTrue(light.isOn(), "isOn() should return true when light is on");
        
        light.turnOff();
        assertEquals("off", light.getState(), "Light should be off after turnOff()");
        assertFalse(light.isOn(), "isOn() should return false when light is off");
    }
    
    @Test
    @DisplayName("Test SmartLight default brightness is 100")
    public void testDefaultBrightness() {
        assertEquals(100, light.getBrightness(), "Default brightness should be 100");
    }
    
    @ParameterizedTest(name = "Test valid brightness value: {0}")
    @ValueSource(ints = {0, 50, 100})
    @DisplayName("Test valid brightness range boundary values")
    public void testValidBrightnessRange(int brightness) {
        light.turnOn();
        light.setBrightness(brightness);
        assertEquals(brightness, light.getBrightness(), 
            "Brightness " + brightness + " should be valid");
    }
    
    @ParameterizedTest(name = "Test invalid brightness value: {0}")
    @ValueSource(ints = {-1, 101, -100, 200})
    @DisplayName("Test invalid brightness values should throw exception")
    public void testInvalidBrightnessThrowsException(int invalidBrightness) {
        light.turnOn();
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> light.setBrightness(invalidBrightness),
            "Setting brightness to " + invalidBrightness + " should throw IllegalArgumentException"
        );
        
        assertNotNull(exception.getMessage(), "Exception message should not be null");
    }
    
    @Test
    @DisplayName("Test set brightness functionality")
    public void testSetBrightnessTurnsOnLight() {
        assertEquals("off", light.getState());
        
        // Note: setBrightness() does not automatically turn on the light
        // Need to turn on first, then set brightness
        light.turnOn();
        light.setBrightness(50);
        assertEquals("on", light.getState(), "Light should be on after turnOn()");
        assertEquals(50, light.getBrightness(), "Brightness should be set correctly");
    }
    
    @ParameterizedTest(name = "Transition from brightness {0} to {1}")
    @CsvSource({
        "0, 100",
        "100, 0",
        "50, 75",
        "25, 25"
    })
    @DisplayName("Test brightness value transitions")
    public void testBrightnessTransition(int from, int to) {
        light.turnOn();
        light.setBrightness(from);
        assertEquals(from, light.getBrightness());
        
        light.setBrightness(to);
        assertEquals(to, light.getBrightness(), 
            "Brightness should transition from " + from + " to " + to);
    }
    
    @Test
    @DisplayName("Test device basic information")
    public void testDeviceInfo() {
        testBasicDeviceOperations();
        
        assertEquals("Light", light.getType(), "Device type should be 'Light'");
        assertNotNull(light.getName(), "Device name should not be null");
    }
    
    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        light.turnOn();
        light.setBrightness(75);
        
        String toString = light.toString();
        assertNotNull(toString, "toString() should not return null");
        assertTrue(toString.contains(light.getName()), "toString should contain device name");
        assertTrue(toString.contains("Light"), "toString should contain device type");
        assertTrue(toString.contains("on"), "toString should contain state");
    }
}





