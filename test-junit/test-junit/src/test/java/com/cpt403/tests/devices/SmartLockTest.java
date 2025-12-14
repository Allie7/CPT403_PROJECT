package com.cpt403.tests.devices;

import SmartHomeManagementSystem.SmartLock;
import SmartHomeManagementSystem.SmartDevice;
import com.cpt403.tests.base.BaseDeviceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SmartLock test class demonstrating OOP principles:
 * Inheritance, Encapsulation, and Polymorphism
 */
@DisplayName("SmartLock Device Tests")
public class SmartLockTest extends BaseDeviceTest {
    
    private SmartLock lock;
    
    /**
     * Factory method - creates SmartLock instance
     */
    @Override
    protected SmartLock createDevice() {
        return new SmartLock(testDeviceName);
    }
    
    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        lock = (SmartLock) device;
    }
    
    @Test
    @DisplayName("Test SmartLock default state is locked")
    public void testDefaultState() {
        assertEquals("locked", lock.getState(), "Lock state should be 'locked' initially");
    }
    
    @Test
    @DisplayName("Test SmartLock unlock functionality")
    public void testUnlock() {
        assertEquals("locked", lock.getState());
        
        lock.unlock();
        // Note: isLocked field may not be synchronized
        // Using getState() is more reliable
        assertEquals("unlocked", lock.getState(), 
            "Lock state should be 'unlocked' after unlock()");
    }
    
    @Test
    @DisplayName("Test SmartLock lock functionality")
    public void testLock() {
        lock.unlock();
        assertEquals("unlocked", lock.getState());
        
        lock.lock();
        assertEquals("locked", lock.getState(), 
            "Lock state should be 'locked' after lock()");
    }
    
    @Test
    @DisplayName("Test lock/unlock state transitions")
    public void testLockUnlockTransition() {
        assertEquals("locked", lock.getState());
        
        lock.unlock();
        assertEquals("unlocked", lock.getState());
        
        lock.lock();
        assertEquals("locked", lock.getState());
    }
    
    @Test
    @DisplayName("Test turnOn is equivalent to unlock")
    public void testTurnOnUnlocks() {
        assertEquals("locked", lock.getState());
        
        lock.turnOn(); // turnOn should be equivalent to unlock
        assertEquals("unlocked", lock.getState(), "turnOn() should unlock the door");
    }
    
    @Test
    @DisplayName("Test turnOff is equivalent to lock")
    public void testTurnOffLocks() {
        lock.unlock();
        assertEquals("unlocked", lock.getState());
        
        lock.turnOff(); // turnOff should be equivalent to lock
        assertEquals("locked", lock.getState(), "turnOff() should lock the door");
    }
    
    @Test
    @DisplayName("Test device basic information")
    public void testDeviceInfo() {
        testBasicDeviceOperations();
        
        assertEquals("Lock", lock.getType(), "Device type should be 'Lock'");
        assertNotNull(lock.getName(), "Device name should not be null");
    }
}





