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
    private static ArrayList<String> legal_states = new ArrayList<>(List.of("locked","unlocked"));

    /**
     * Constructor
     * Default state is locked
     *
     * @param name Door Lock Equipment Name
     */
    public SmartLock(String name) {
        super(name, "Lock");
        this.isLocked = true;
        setState("locked");
    }

    /**
     * Set Door Lock State
     *
     * @param state The state to set (locked/unlocked/on/off)
     * @throws IllegalArgumentException If the state is invalid
     */
    @Override
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        String lowerState = state.toLowerCase();

        // Supports multiple state representations
        if (lowerState.equals("on")) {
            lowerState = "unlocked";
        } else if (lowerState.equals("off")) {
            lowerState = "locked";
        }

        // Verify whether the status is valid
        if (!legal_states.contains(lowerState)) {
            throw new IllegalArgumentException("Invalid state for SmartLock: " + state + ". Must be 'locked', 'unlocked', 'on', or 'off'");
        }

        // Update status and isLocked flag
        super.setState(lowerState);
        this.isLocked = lowerState.equals("locked");
    }

    /**
     * Lock the door
     */
    public void lock() {
        setState("locked");
    }

    /**
     * Unlock the door lock
     */
    public void unlock() {
        setState("unlocked");
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