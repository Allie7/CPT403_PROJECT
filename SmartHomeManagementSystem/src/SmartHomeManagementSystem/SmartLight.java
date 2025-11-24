package SmartHomeManagementSystem;

public class SmartLight extends SmartDevice {
    private int brightness; // 亮度 0-100

    public SmartLight(String name) {
        super(name, "Light");
        this.brightness = 100; // 默认亮度
    }

    // Setter 方法
    public void setState(String state) {
        this.state = state;
    }

    /**
     * kind of redundant
     */
    @Override
    public void turnOn() {
        setState("on");
        System.out.println(getName() + " 灯已开启，亮度: " + brightness + "%");
    }

    @Override
    public void turnOff() {
        setState("off");
        System.out.println(getName() + " 灯已关闭");
    }

    // 特定于灯的方法
    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
            if (isOn()) {
                System.out.println(getName() + " 亮度调整为: " + brightness + "%");
            }
        }
    }

    public int getBrightness() {
        return brightness;
    }
}
