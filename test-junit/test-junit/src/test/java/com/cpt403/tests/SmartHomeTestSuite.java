package com.cpt403.tests;

import com.cpt403.tests.devices.SmartLightTest;
import com.cpt403.tests.devices.SmartThermostatTest;
import com.cpt403.tests.devices.SmartLockTest;
import com.cpt403.tests.groups.DeviceGroupTest;
import com.cpt403.tests.scenes.SceneTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite demonstrating OOP principles:
 * Composition and Encapsulation
 * Uses JUnit 5 Suite to organize all tests
 */
@Suite
@SuiteDisplayName("Smart Home System Complete Test Suite")
@SelectClasses({
    SmartLightTest.class,
    SmartThermostatTest.class,
    SmartLockTest.class,
    DeviceGroupTest.class,
    SceneTest.class
})
public class SmartHomeTestSuite {
    // Test suite class for running all tests
}






