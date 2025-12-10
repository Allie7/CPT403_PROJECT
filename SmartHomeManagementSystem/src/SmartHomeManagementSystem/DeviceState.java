package SmartHomeManagementSystem;

/**
 * Device state enumeration to restrict valid states.
 * Currently supports ON/OFF for generic devices and LOCKED/UNLOCKED for locks.
 */
public enum DeviceState {
    ON,
    OFF,
    LOCKED,
    UNLOCKED
}
