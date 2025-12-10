import SmartHomeManagementSystem.SmartLight;
import SmartHomeManagementSystem.SmartLock;
import SmartHomeManagementSystem.SmartThermostat;
import SmartHomeManagementSystem.User;
import com.yulusi.tests.framework.Assertions;
import com.yulusi.tests.framework.TestHarness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specification level tests derived directly from the CPT403 phase-1 requirements.
 *
 * The same source file can be compiled and executed against each branch by pointing the classpath
 * at the corresponding source tree, ensuring we never modify the branch contents themselves.
 */
public final class SmartHomeSpecificationTests {

    private final String branchName;
    private final TestHarness harness;

    public SmartHomeSpecificationTests(String branchName) {
        this.branchName = branchName;
        this.harness = new TestHarness("SmartHome Spec - " + branchName);
    }

    public static void main(String[] args) {
        String branch = args.length > 0 ? args[0] : "unspecified-branch";
        SmartHomeSpecificationTests suite = new SmartHomeSpecificationTests(branch);
        suite.runAll();
    }

    private void runAll() {
        testSmartLightControls();
        testThermostatTemperatureRange();
        testSmartLockStates();
        testDeviceGroupTypeSafety();
        testLightGroupManagement();
        testThermostatGroupManagement();
        testUnknownGroupRejection();
        testSceneExecution();
        harness.summarize();
    }

    private void testSmartLightControls() {
        harness.run("SmartLight brightness bounds", () -> {
            SmartLight light = new SmartLight(branchName + "-living-room");
            Assertions.assertEquals("off", light.getState(), "Lights should default to off");
            light.turnOn();
            Assertions.assertEquals("on", light.getState(), "turnOn should switch to on");
            light.setBrightness(40);
            Assertions.assertEquals(40, light.getBrightness(), "Brightness should be updated");
            // Test boundary values
            light.setBrightness(0);
            Assertions.assertEquals(0, light.getBrightness(), "Brightness 0 should be valid");
            light.setBrightness(100);
            Assertions.assertEquals(100, light.getBrightness(), "Brightness 100 should be valid");
            // Test invalid values
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> light.setBrightness(-1),
                    "Brightness below 0 must be rejected");
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> light.setBrightness(101),
                    "Brightness above 100 must be rejected");
        });
    }

    private void testThermostatTemperatureRange() {
        harness.run("SmartThermostat temperature constraints", () -> {
            SmartThermostat thermostat = new SmartThermostat(branchName + "-thermostat");
            thermostat.turnOn();
            thermostat.setTemperature(23.5);
            Assertions.assertEquals(23.5, thermostat.getTemperature(), 0.001,
                    "Thermostat should hold the configured temperature");
            // Test boundary values
            thermostat.setTemperature(0.0);
            Assertions.assertEquals(0.0, thermostat.getTemperature(), 0.001,
                    "Temperature 0.0°C should be valid");
            thermostat.setTemperature(40.0);
            Assertions.assertEquals(40.0, thermostat.getTemperature(), 0.001,
                    "Temperature 40.0°C should be valid");
            // Test invalid values
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> thermostat.setTemperature(-5),
                    "Thermostat must reject values below 0°C");
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> thermostat.setTemperature(60),
                    "Thermostat must reject values above 40°C");
        });
    }

    private void testSmartLockStates() {
        harness.run("SmartLock lock/unlock workflow", () -> {
            SmartLock lock = new SmartLock(branchName + "-front-door");
            Assertions.assertTrue(lock.isLocked(), "Locks should start in locked state");
            lock.unlock();
            Assertions.assertFalse(lock.isLocked(), "unlock() should mark lock as unlocked");
            lock.lock();
            Assertions.assertTrue(lock.isLocked(), "lock() should relock the door");
        });
    }

    private void testDeviceGroupTypeSafety() {
        harness.run("DeviceGroup enforces type homogeneity", () -> {
            User user = new User("tester-" + branchName);
            SmartLight light = new SmartLight(branchName + "-light");
            SmartThermostat thermostat = new SmartThermostat(branchName + "-thermo");
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> user.createGroup("Mixed-" + branchName, new ArrayList<>(List.of(light, thermostat))),
                    "Mixed device types must not be allowed in a single group");
        });
    }

    private void testLightGroupManagement() {
        harness.run("Light group management propagates brightness", () -> {
            User user = new User("tester-" + branchName);
            SmartLight lightA = new SmartLight(branchName + "-lightA");
            SmartLight lightB = new SmartLight(branchName + "-lightB");
            user.addDeviceToHub(lightA);
            user.addDeviceToHub(lightB);
            user.createGroup("LivingLights-" + branchName, new ArrayList<>(List.of(lightA, lightB)));
            user.manageGroup("LivingLights-" + branchName, 25);
            Assertions.assertEquals("on", lightA.getState(), "Group brightness update should turn lights on");
            Assertions.assertEquals("on", lightB.getState(), "Group brightness update should turn lights on");
            Assertions.assertEquals(25, lightA.getBrightness(), "Light A should match requested brightness");
            Assertions.assertEquals(25, lightB.getBrightness(), "Light B should match requested brightness");
        });
    }

    private void testThermostatGroupManagement() {
        harness.run("Thermostat group propagates temperature", () -> {
            User user = new User("tester-" + branchName);
            SmartThermostat thermostatA = new SmartThermostat(branchName + "-thermostatA");
            SmartThermostat thermostatB = new SmartThermostat(branchName + "-thermostatB");
            user.addDeviceToHub(thermostatA);
            user.addDeviceToHub(thermostatB);
            user.createGroup("Heat-" + branchName, new ArrayList<>(List.of(thermostatA, thermostatB)));
            user.manageGroup("Heat-" + branchName, 19.0);
            Assertions.assertEquals("on", thermostatA.getState(), "Managing group should wake thermostat A");
            Assertions.assertEquals("on", thermostatB.getState(), "Managing group should wake thermostat B");
            Assertions.assertEquals(19.0, thermostatA.getTemperature(), 0.001,
                    "Thermostat A should match requested temperature");
            Assertions.assertEquals(19.0, thermostatB.getTemperature(), 0.001,
                    "Thermostat B should match requested temperature");
        });
    }

    private void testUnknownGroupRejection() {
        harness.run("Unknown groups are rejected", () -> {
            User user = new User("tester-" + branchName);
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> user.manageGroup("non-existent-" + branchName, 50),
                    "Hub should throw if a group name is unknown for brightness");
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> user.manageGroup("non-existent-" + branchName, 20.0),
                    "Hub should throw if a group name is unknown for temperature");
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> user.turnOnGroup("non-existent-" + branchName),
                    "Hub should throw if a group name is unknown for state control");
        });
    }

    private void testSceneExecution() {
        harness.run("Scenes apply multi-device states", () -> {
            User user = new User("tester-" + branchName);
            SmartLight sceneLight = new SmartLight(branchName + "-scene-light");
            SmartThermostat sceneThermostat = new SmartThermostat(branchName + "-scene-thermostat");
            SmartLock sceneLock = new SmartLock(branchName + "-scene-lock");

            user.addDeviceToHub(sceneLight);
            user.addDeviceToHub(sceneThermostat);
            user.addDeviceToHub(sceneLock);

            Map<String, String> sceneConfig = new HashMap<>();
            sceneConfig.put(sceneLight.getName(), "15");
            sceneConfig.put(sceneThermostat.getName(), "18.5");
            sceneConfig.put(sceneLock.getName(), "locked");

            user.createScene("MovieNight-" + branchName, sceneConfig);
            user.runScene("MovieNight-" + branchName);

            Assertions.assertEquals("on", sceneLight.getState(), "Scene should turn the light on");
            Assertions.assertEquals(15, sceneLight.getBrightness(), "Scene should dim the light");
            Assertions.assertEquals(18.5, sceneThermostat.getTemperature(), 0.001,
                    "Scene should set thermostat temperature");
            Assertions.assertTrue(sceneLock.isLocked(), "Scene should lock the door");
        });
    }
}
