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
        System.out.println(getName() + " 恒温器已开启，温度: " + temperature + "°C，模式: " + mode);
    }

    @Override
    public void turnOff() {
        setState("off");
        System.out.println(getName() + " 恒温器已关闭");
    }

    // 特定于恒温器的方法
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        if (isOn()) {
            System.out.println(getName() + " 温度设置为: " + temperature + "°C");
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
        if (isOn()) {
            System.out.println(getName() + " 模式设置为: " + mode);
        }
    }
}