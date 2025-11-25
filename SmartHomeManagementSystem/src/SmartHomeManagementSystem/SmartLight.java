package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class SmartLight extends SmartDevice {
    private int brightness; // 亮度 0-100
    private static ArrayList<String> legal_states = new ArrayList<>(List.of("on","off"));

    public SmartLight(String name) {
        super(name, "Light");
        this.brightness = 100; // 默认亮度
    }


    public void setState(String state) {
        this.state = state;
    }    // Setter 方法

    /**
     * kind of redundant
     */
    @Override
    public void turnOn() {
        setState("on");
    }

    @Override
    public void turnOff() {
        setState("off");
    }

    // 特定于灯的方法
    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            throw new IllegalArgumentException("Invalid brightness");
        }
    }

    public int getBrightness() {
        return brightness;
    }
    @Override
    public String toString() {
        return String.format("SmartLight{name='%s', type='%s', state='%s', Brightness=%d}",
                this.name, this.type, this.state, this.brightness);
    }
}
