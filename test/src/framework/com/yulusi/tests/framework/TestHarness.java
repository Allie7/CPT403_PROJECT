package com.yulusi.tests.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extremely small test harness that keeps track of pass/fail counts and prints readable output.
 */
public class TestHarness {
    private final String suiteName;
    private final List<String> failures = new ArrayList<>();
    private int executed;

    public TestHarness(String suiteName) {
        this.suiteName = suiteName;
    }

    public void run(String testName, ThrowingRunnable test) {
        executed++;
        try {
            test.run();
            System.out.println("[PASS] " + format(testName));
        } catch (AssertionError assertionError) {
            recordFailure(testName, assertionError);
        } catch (Throwable throwable) {
            recordFailure(testName, throwable);
        }
    }

    private void recordFailure(String testName, Throwable throwable) {
        String failureMessage = format(testName) + " -> " + throwable.getMessage();
        failures.add(failureMessage);
        System.err.println("[FAIL] " + failureMessage);
    }

    private String format(String testName) {
        return suiteName + " :: " + testName;
    }

    public void summarize() {
        int passed = executed - failures.size();
        System.out.println("Suite '" + suiteName + "' finished. Passed: " + passed + ", Failed: " + failures.size());
        if (!failures.isEmpty()) {
            throw new AssertionError("Failures detected:\n" + String.join("\n", failures));
        }
    }

    public List<String> getFailures() {
        return Collections.unmodifiableList(failures);
    }
}


