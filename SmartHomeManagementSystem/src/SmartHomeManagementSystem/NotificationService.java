package SmartHomeManagementSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Notification Service (通知服务)
 *
 * Implements the Observer interface to receive device state changes
 * and generate notifications for users.
 *
 * This class demonstrates the Observer Pattern:
 * - It "observes" devices and reacts to their state changes
 * - Decoupled from device implementation details
 */
public class NotificationService implements Observer {

    private List<String> notificationHistory;
    private boolean enabled;
    private DateTimeFormatter formatter;

    /**
     * Constructor
     */
    public NotificationService() {
        this.notificationHistory = new ArrayList<>();
        this.enabled = true;
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Receives updates when a device state changes
     * This method is called automatically by the device (Subject)
     *
     * @param device The device that changed
     * @param oldState The previous state
     * @param newState The new state
     */
    @Override
    public void update(SmartDevice device, String oldState, String newState) {
        if (!enabled) {
            return;
        }

        String timestamp = LocalDateTime.now().format(formatter);
        String message = generateNotification(device, oldState, newState, timestamp);

        // Store in history
        notificationHistory.add(message);

        // Print notification (simulating sending to user)
        System.out.println("NOTIFICATION: " + message);
    }

    /**
     * Generate appropriate notification message based on device type
     */
    private String generateNotification(SmartDevice device, String oldState, String newState, String timestamp) {
        String deviceName = device.getName();
        String deviceType = device.getType();

        // Generate specific messages based on device type
        if (deviceType.equals("Lock")) {
            if (newState.equals("unlocked")) {
                return String.format("[%s] ALERT: %s has been UNLOCKED!", timestamp, deviceName);
            } else if (newState.equals("locked")) {
                return String.format("[%s] %s is now locked.", timestamp, deviceName);
            }
        } else if (deviceType.equals("Light")) {
            if (newState.equals("on")) {
                return String.format("[%s] %s turned ON.", timestamp, deviceName);
            } else if (newState.equals("off")) {
                return String.format("[%s] %s turned OFF.", timestamp, deviceName);
            }
        } else if (deviceType.equals("Thermostat")) {
            if (newState.equals("on")) {
                return String.format("[%s] %s is now ON.", timestamp, deviceName);
            } else if (newState.equals("off")) {
                return String.format("[%s] %s turned OFF.", timestamp, deviceName);
            }
        }

        // Default message
        return String.format("[%s] %s changed from %s to %s", timestamp, deviceName, oldState, newState);
    }

    /**
     * Enable notifications
     */
    public void enable() {
        this.enabled = true;
        System.out.println("Notifications enabled.");
    }

    /**
     * Disable notifications
     */
    public void disable() {
        this.enabled = false;
        System.out.println("Notifications disabled.");
    }

    /**
     * Check if notifications are enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Get all notification history
     */
    public List<String> getNotificationHistory() {
        return new ArrayList<>(notificationHistory);
    }

    /**
     * Print all notification history
     */
    public void viewHistory() {
        System.out.println("\n=== Notification History ===");
        if (notificationHistory.isEmpty()) {
            System.out.println("No notifications yet.");
        } else {
            for (String notification : notificationHistory) {
                System.out.println(notification);
            }
        }
        System.out.println("============================\n");
    }

    /**
     * Clear notification history
     */
    public void clearHistory() {
        notificationHistory.clear();
        System.out.println("Notification history cleared.");
    }

    /**
     * Get the count of notifications
     */
    public int getNotificationCount() {
        return notificationHistory.size();
    }
}