package com.cpt403.tests.base;

import SmartHomeManagementSystem.SmartDevice;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract base class for device tests demonstrating OOP principles:
 * Abstraction, Inheritance, Encapsulation, and Polymorphism
 */
public abstract class BaseDeviceTest {
    
    protected String testDeviceName;
    protected SmartDevice device;
    
    /**
     * Template method - defines common test setup flow
     */
    @BeforeEach
    public void setUp() {
        testDeviceName = generateDeviceName();
        device = createDevice();
    }
    
    /**
     * Factory method - subclasses must implement to create device instances
     */
    protected abstract SmartDevice createDevice();
    
    /**
     * Generates device name - subclasses can override for specific names
     */
    protected String generateDeviceName() {
        return "test-device-" + getClass().getSimpleName().toLowerCase();
    }
    
    /**
     * Common test method - all devices should have these basic operations
     */
    protected void testBasicDeviceOperations() {
        assertNotNull(device, "Device should not be null");
        assertNotNull(device.getName(), "Device name should not be null");
        assertFalse(device.getName().isEmpty(), "Device name should not be empty");
        
        assertNotNull(device.getType(), "Device type should not be null");
    }
    
    /**
     * Common state verification method
     */
    protected void verifyDeviceState(String expectedState) {
        assertNotNull(device.getState(), "Device state should not be null");
        assertEquals(expectedState, device.getState(), 
            "Expected state: " + expectedState + ", but got: " + device.getState());
    }
}

