package SmartHomeManagementSystem;

public class SmartThermostat extends SmartDevice {
    private double temperature;
    private String mode; // heat, cool, auto

    public SmartThermostat(String name) {
        super(name, "Thermostat");
        this.temperature = 22.0; // 默认温度
        /**
         * mode is unncessary
         */
        // this.mode = "auto";
    }

    @Override
    public void turnOn() {
        setState("on");
    }

    @Override
    public void turnOff() {
        setState("off");
    }

    // 特定于恒温器的方法
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    // 特定于恒温器的方法
    public double getTemperature() {
        return temperature;
    }
}