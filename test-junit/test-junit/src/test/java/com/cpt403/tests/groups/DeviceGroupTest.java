package com.cpt403.tests.groups;

import SmartHomeManagementSystem.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DeviceGroup test class demonstrating OOP principles:
 * Encapsulation and Polymorphism
 */
@DisplayName("Device Group Management Tests")
public class DeviceGroupTest {
    
    private User user;
    private SmartLight light1;
    private SmartLight light2;
    private SmartThermostat thermostat1;
    private SmartThermostat thermostat2;
    
    @BeforeEach
    public void setUp() {
        user = new User("test-user");
        light1 = new SmartLight("light-1");
        light2 = new SmartLight("light-2");
        thermostat1 = new SmartThermostat("thermo-1");
        thermostat2 = new SmartThermostat("thermo-2");
    }
    
    @Test
    @DisplayName("Test homogeneous group creation - same type devices can be grouped")
    public void testHomogeneousGroupCreation() {
        user.addDeviceToHub(light1);
        user.addDeviceToHub(light2);
        
        ArrayList<SmartDevice> lights = new ArrayList<>();
        lights.add(light1);
        lights.add(light2);
        assertDoesNotThrow(() -> {
            user.createGroup("LightGroup", lights);
        }, "Creating group with same device types should not throw exception");
    }
    
    @Test
    @DisplayName("Test mixed type group rejection - different type devices cannot be grouped")
    public void testMixedTypeGroupRejection() {
        user.addDeviceToHub(light1);
        user.addDeviceToHub(thermostat1);
        
        ArrayList<SmartDevice> mixedDevices = new ArrayList<>();
        mixedDevices.add(light1);
        mixedDevices.add(thermostat1);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> user.createGroup("MixedGroup", mixedDevices),
            "Mixed device types must not be allowed in a single group"
        );
        
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("same type") || 
                   exception.getMessage().contains("type"),
            "Exception message should mention device type");
    }
    
    @Test
    @DisplayName("Test light group brightness management")
    public void testLightGroupBrightnessManagement() {
        user.addDeviceToHub(light1);
        user.addDeviceToHub(light2);
        
        ArrayList<SmartDevice> lights = new ArrayList<>();
        lights.add(light1);
        lights.add(light2);
        
        user.createGroup("LivingLights", lights);
        user.manageGroupBrightness("LivingLights", 25);
        
        assertEquals("on", light1.getState(), 
            "Group brightness update should turn lights on");
        assertEquals("on", light2.getState(), 
            "Group brightness update should turn lights on");
        assertEquals(25, light1.getBrightness(), 
            "Light 1 should match requested brightness");
        assertEquals(25, light2.getBrightness(), 
            "Light 2 should match requested brightness");
    }
    
    @Test
    @DisplayName("Test thermostat group temperature management")
    public void testThermostatGroupTemperatureManagement() {
        user.addDeviceToHub(thermostat1);
        user.addDeviceToHub(thermostat2);
        
        ArrayList<SmartDevice> thermostats = new ArrayList<>();
        thermostats.add(thermostat1);
        thermostats.add(thermostat2);
        
        user.createGroup("HeatGroup", thermostats);
        user.manageGroupTemperature("HeatGroup", 19.0);
        
        assertEquals("on", thermostat1.getState(), 
            "Managing group should wake thermostat 1");
        assertEquals("on", thermostat2.getState(), 
            "Managing group should wake thermostat 2");
        assertEquals(19.0, thermostat1.getTemperature(), 0.001,
            "Thermostat 1 should match requested temperature");
        assertEquals(19.0, thermostat2.getTemperature(), 0.001,
            "Thermostat 2 should match requested temperature");
    }
    
    @Test
    @DisplayName("Test unknown group name should throw exception")
    public void testUnknownGroupRejection() {
        assertThrows(IllegalArgumentException.class,
            () -> user.manageGroupBrightness("non-existent-group", 50),
            "Hub should throw if a group name is unknown for brightness");
        
        assertThrows(IllegalArgumentException.class,
            () -> user.manageGroupTemperature("non-existent-group", 20.0),
            "Hub should throw if a group name is unknown for temperature");
        
        assertThrows(IllegalArgumentException.class,
            () -> user.turnOnGroup("non-existent-group"),
            "Hub should throw if a group name is unknown for state control");
        
        assertThrows(IllegalArgumentException.class,
            () -> user.turnOffGroup("non-existent-group"),
            "Hub should throw if a group name is unknown for state control");
    }
    
    @Test
    @DisplayName("Test device group on/off control")
    public void testGroupOnOffControl() {
        user.addDeviceToHub(light1);
        user.addDeviceToHub(light2);
        
        ArrayList<SmartDevice> lights = new ArrayList<>();
        lights.add(light1);
        lights.add(light2);
        
        user.createGroup("TestLights", lights);
        
        user.turnOnGroup("TestLights");
        assertEquals("on", light1.getState());
        assertEquals("on", light2.getState());
        
        user.turnOffGroup("TestLights");
        assertEquals("off", light1.getState());
        assertEquals("off", light2.getState());
    }
}





