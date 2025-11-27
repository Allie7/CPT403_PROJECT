### Smart Home Specification Tests

This folder hosts branch-agnostic specification tests derived from `CPT403_GroupProject_Phase1.pdf`.  
The tests target the shared interfaces exposed by `SmartHomeManagementSystem` and never modify the contents of
`CPT403_PROJECT-main`, `CPT403_PROJECT-XinyiJi`, or `CPT403_PROJECT-YuxuanXie`.

#### What is covered
- SmartLight must enforce on/off defaults and 0–100 brightness bounds.
- SmartThermostat must clamp temperatures to the 0–40 °C range.
- SmartLock must reliably toggle between locked/unlocked states and reject invalid states.
- Device groups must be homogeneous, and hub-level group commands must propagate brightness/temperature settings.
- Unknown groups must be rejected.
- Scenes created through `User.createScene` must apply multi-device states exactly once executed via `User.runScene`.

These checks map directly to the "Devices", "Smart Home Hub", "Grouping Devices", and "Creating and Activating Scenes"
sections of the phase-1 brief.

#### Layout
```
YuluSi/
  src/
    framework/      # Minimal assertion + harness utilities (no external libs)
    tests/          # Branch-agnostic SmartHomeSpecificationTests
  run_all_tests.sh  # Helper script to compile each branch and execute the suite
```

#### Running the tests

Make sure a JDK (17 or newer) is available on your `PATH` so that both `javac` and `java` commands can run.

```
cd /Users/mac/Desktop/CPT403_PROJECT_副本/YuluSi
zsh run_all_tests.sh
```

The script will:
1. Compile each branch (`main`, `xinyiji`, `yuxuanxie`) into `YuluSi/build/<branch>/classes`.
2. Compile the shared tests against that branch's bytecode.
3. Execute `com.yulusi.tests.suites.SmartHomeSpecificationTests <branch>` to produce a PASS/FAIL summary.

Exit code `0` means every branch satisfied the checks; any other code indicates at least one regression.  
You can also run a single branch manually by invoking `java -cp <branch_classes>:<test_classes> com.yulusi.tests.suites.SmartHomeSpecificationTests <branchName>`.

#### Notes
- The harness is intentionally lightweight so it can run with the JDK alone—no Gradle/Maven/JUnit setup required.
- If compilation fails for a branch, it signals that the branch no longer satisfies the common public API expected
  by the phase-1 brief.
- Test names in the output correspond to individual requirements, making it easy to trace regressions back to the
  relevant feature area.

