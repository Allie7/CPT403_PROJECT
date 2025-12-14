package com.cpt403.tests.scenes;

import SmartHomeManagementSystem.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Scene test class demonstrating OOP principles:
 * Encapsulation and Composition
 */
@DisplayName("Scene Management Tests")
public class SceneTest {
    
    private User user;
    private SmartLight sceneLight;
    private SmartThermostat sceneThermostat;
    private SmartLock sceneLock;
    
    @BeforeEach
    public void setUp() {
        user = new User("test-user");
        sceneLight = new SmartLight("scene-light");
        sceneThermostat = new SmartThermostat("scene-thermostat");
        sceneLock = new SmartLock("scene-lock");
        
        user.addDeviceToHub(sceneLight);
        user.addDeviceToHub(sceneThermostat);
        user.addDeviceToHub(sceneLock);
    }
    
    @Test
    @DisplayName("Test scene creation and execution")
    public void testSceneCreationAndExecution() {
        Map<String, String> sceneConfig = new HashMap<>();
        sceneConfig.put(sceneLight.getName(), "15");
        sceneConfig.put(sceneThermostat.getName(), "18.5");
        sceneConfig.put(sceneLock.getName(), "locked");
        
        Scene scene = user.createScene("MovieNight", sceneConfig);
        assertNotNull(scene, "Scene should be created");
        assertEquals("MovieNight", scene.getName(), "Scene name should match");
        
        user.runScene("MovieNight");
        
        assertEquals("on", sceneLight.getState(), "Scene should turn the light on");
        assertEquals(15, sceneLight.getBrightness(), "Scene should dim the light");
        assertEquals(18.5, sceneThermostat.getTemperature(), 0.001,
            "Scene should set thermostat temperature");
        assertEquals("locked", sceneLock.getState(), "Scene should lock the door");
    }
    
    @Test
    @DisplayName("Test scene execution consistency across multiple runs")
    public void testSceneExecutionConsistency() {
        Map<String, String> sceneConfig = new HashMap<>();
        sceneConfig.put(sceneLight.getName(), "50");
        sceneConfig.put(sceneThermostat.getName(), "22.0");
        
        user.createScene("TestScene", sceneConfig);
        
        user.runScene("TestScene");
        assertEquals(50, sceneLight.getBrightness());
        assertEquals(22.0, sceneThermostat.getTemperature(), 0.001);
        
        sceneLight.setBrightness(100);
        sceneThermostat.setTemperature(25.0);
        
        user.runScene("TestScene");
        assertEquals(50, sceneLight.getBrightness(), 
            "Scene should restore configured brightness");
        assertEquals(22.0, sceneThermostat.getTemperature(), 0.001,
            "Scene should restore configured temperature");
    }
    
    @Test
    @DisplayName("Test executing non-existent scene should throw exception")
    public void testNonExistentSceneExecution() {
        assertThrows(IllegalArgumentException.class,
            () -> user.runScene("NonExistentScene"),
            "Executing non-existent scene should throw exception");
    }
    
    @Test
    @DisplayName("Test scene with multiple device types")
    public void testSceneWithMultipleDeviceTypes() {
        Map<String, String> complexScene = new HashMap<>();
        complexScene.put(sceneLight.getName(), "30");
        complexScene.put(sceneThermostat.getName(), "20.0");
        complexScene.put(sceneLock.getName(), "unlocked");
        
        user.createScene("ComplexScene", complexScene);
        user.runScene("ComplexScene");
        
        assertEquals("on", sceneLight.getState());
        assertEquals(30, sceneLight.getBrightness());
        assertEquals("on", sceneThermostat.getState());
        assertEquals(20.0, sceneThermostat.getTemperature(), 0.001);
        assertEquals("unlocked", sceneLock.getState());
        // Note: isLocked field may not be synchronized
        // Using getState() is more reliable
    }
}





