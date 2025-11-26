package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能灯光类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供灯光特定的功能
 *
 */
public class SmartLight extends SmartDevice {
    // 亮度范围常量
    private static final int MIN_BRIGHTNESS = 0;
    private static final int MAX_BRIGHTNESS = 100;

    private int brightness; // 亮度 0-100
    private static ArrayList<String> legal_states = new ArrayList<>(List.of("on","off"));

    /**
     * 构造方法
     *
     * @param name 灯光设备名称
     */
    public SmartLight(String name) {
        super(name, "Light");
        this.brightness = 100; // 默认亮度为100%
    }

    /**
     * 设置灯光状态
     *
     * @param state 要设置的状态（on/off）
     * @throws IllegalArgumentException 如果状态不合法
     */
    @Override
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        String lowerState = state.toLowerCase();
        if (!legal_states.contains(lowerState)) {
            throw new IllegalArgumentException("Invalid state for SmartLight: " + state + ". Must be 'on' or 'off'");
        }
        super.setState(lowerState);
    }

    /**
     * 打开灯光
     */
    @Override
    public void turnOn() {
        setState("on");
    }

    /**
     * 关闭灯光
     */
    @Override
    public void turnOff() {
        setState("off");
    }

    /**
     * 设置灯光亮度
     * 注意：设置亮度会自动打开灯光
     *
     * @param brightness 亮度值（0-100之间）
     * @throws IllegalArgumentException 如果亮度值不在有效范围内
     */
    public void setBrightness(int brightness) {
        if (brightness < MIN_BRIGHTNESS || brightness > MAX_BRIGHTNESS) {
            throw new IllegalArgumentException(
                    String.format("Invalid brightness: %d. Must be between %d and %d",
                            brightness, MIN_BRIGHTNESS, MAX_BRIGHTNESS)
            );
        }
        this.brightness = brightness;
    }

    /**
     * 获取当前亮度
     *
     * @return 当前亮度值（0-100）
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * 返回灯光设备的字符串表示
     * 包含名称、类型、状态和亮度信息
     *
     * @return 格式化的设备信息字符串
     */
    @Override
    public String toString() {
        return String.format("SmartLight{name='%s', type='%s', state='%s', Brightness=%d}",
                this.getName(), this.getType(), this.getState(), this.brightness);
    }
}