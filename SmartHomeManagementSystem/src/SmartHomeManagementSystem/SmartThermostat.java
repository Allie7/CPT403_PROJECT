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

    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s', Temperature=%f}",
                this.name, this.type, this.state,this.temperature);
    }
}