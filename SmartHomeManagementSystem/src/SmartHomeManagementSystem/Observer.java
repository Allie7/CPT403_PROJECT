package SmartHomeManagementSystem;

/**
 * Observer Interface (观察者接口)
 *
 * Part of the Observer Pattern implementation.
 * Any class that wants to receive notifications about device state changes
 * should implement this interface.
 */
public interface Observer {

    /**
     * Called when a device's state changes
     *
     * @param device The device that changed
     * @param oldState The previous state
     * @param newState The new state
     */
    void update(SmartDevice device, String oldState, String newState);
}