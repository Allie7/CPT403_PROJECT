package com.yulusi.tests.framework;

/**
 * Minimal assertion helpers so we do not depend on external testing libraries.
 */
public final class Assertions {

    private Assertions() {
        // Utility class
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

    public static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        throw new AssertionError(String.format("%s (expected='%s', actual='%s')", message, expected, actual));
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(String.format("%s (expected=%d, actual=%d)", message, expected, actual));
        }
    }

    public static void assertEquals(double expected, double actual, double delta, String message) {
        if (Double.isNaN(expected) && Double.isNaN(actual)) {
            return;
        }
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(String.format(
                    "%s (expected=%.4f, actual=%.4f, delta=%.4f)", message, expected, actual, delta));
        }
    }

    public static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void assertThrows(Class<? extends Throwable> expected,
                                    ThrowingRunnable executable,
                                    String message) {
        try {
            executable.run();
        } catch (Throwable throwable) {
            if (expected.isInstance(throwable)) {
                return;
            }
            throw new AssertionError(
                    String.format("%s (expected %s but caught %s)",
                            message, expected.getSimpleName(), throwable.getClass().getSimpleName()),
                    throwable);
        }
        throw new AssertionError(String.format("%s (expected %s but nothing was thrown)",
                message, expected.getSimpleName()));
    }
}


