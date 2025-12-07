package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Smart Door Lock Category - Specific Implementation of SmartDevice
 * Inherits from the abstract class SmartDevice, providing door lock-specific functionality
 *
 */
public class SmartLock extends SmartDevice {
    private boolean isLocked;
    //private static ArrayList<String> legalStates = new ArrayList<>(List.of("locked","unlocked"));

    /**
     * Constructor
     * Default state is locked
     *
     * @param name Door Lock Equipment Name
     */
    public SmartLock(String name) {
        super(name, "Lock");
        this.isLocked = true;
        setState(DeviceState.LOCKED);
    }


    /**
     * Lock the door
     */
    public void lock() {
        setState(DeviceState.LOCKED);
    }

    /**
     * Unlock the door lock
     */
    public void unlock() {
        setState(DeviceState.UNLOCKED);
    }

    /**
     * Unlock the door
     */
    @Override
    public void turnOn() {
        unlock();
    }

    /**
     * Lock the door
     */
    @Override
    public void turnOff() {
        lock();
    }

    /**
     * Check if the door lock is in the locked state
     *
     * @return Returns true if the door lock is locked, false otherwise
     */
    public boolean isLocked() {
        return this.isLocked;
    }
}