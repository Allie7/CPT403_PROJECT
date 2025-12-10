package SmartHomeManagementSystem;

/**
 * Notification System Demo
 *
 * Demonstrates the Observer Pattern implementation:
 * - NotificationService observes devices
 * - When device state changes, notification is automatically triggered
 */
public class NotificationDemo {

    public static void main(String[] args) {
        System.out.println("=== Smart Home Notification System Demo ===\n");

        // Step 1: Create notification service (Observer)
        NotificationService notificationService = new NotificationService();

        // Step 2: Create devices (Subjects)
        SmartLight livingRoomLight = new SmartLight("Living Room Light");
        SmartLock frontDoor = new SmartLock("Front Door Lock");
        SmartThermostat thermostat = new SmartThermostat("Living Room Thermostat");

        // Step 3: Register notification service as observer for each device
        livingRoomLight.addObserver(notificationService);
        frontDoor.addObserver(notificationService);
        thermostat.addObserver(notificationService);

        System.out.println("Devices created and notification service registered.\n");
        System.out.println("--- Testing Device Operations ---\n");

        // Step 4: Operate devices - notifications will be triggered automatically
        System.out.println(">> Turning on living room light...");
        livingRoomLight.turnOn();

        System.out.println("\n>> Unlocking front door...");
        frontDoor.unlock();

        System.out.println("\n>> Locking front door...");
        frontDoor.lock();

        System.out.println("\n>> Turning on thermostat...");
        thermostat.turnOn();

        System.out.println("\n>> Turning off living room light...");
        livingRoomLight.turnOff();

        // Step 5: View notification history
        notificationService.viewHistory();

        // Step 6: Demonstrate disable/enable
        System.out.println("--- Testing Notification Toggle ---\n");
        notificationService.disable();

        System.out.println(">> Turning on light (notifications disabled)...");
        livingRoomLight.turnOn();

        notificationService.enable();
        System.out.println(">> Turning off light (notifications enabled)...");
        livingRoomLight.turnOff();

        // Final history
        notificationService.viewHistory();

        System.out.println("=== Demo Complete ===");
    }
}