package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能温控器类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供温度控制功能
 *
 */
public class SmartThermostat extends SmartDevice {
    // 温度范围常量（摄氏度）
    private static final double MIN_TEMPERATURE = 0.0;
    private static final double MAX_TEMPERATURE = 40.0;

    private double temperature;
    private static ArrayList<String> legal_states = new ArrayList<>(List.of("on","off"));

    /**
     * 构造方法
     *
     * @param name 温控器设备名称
     */
    public SmartThermostat(String name) {
        super(name, "Thermostat");
        this.temperature = 22.0; // 默认温度22°C
    }

    /**
     * 设置温控器状态
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
            throw new IllegalArgumentException("Invalid state for SmartThermostat: " + state + ". Must be 'on' or 'off'");
        }
        super.setState(lowerState);
    }

    /**
     * 打开温控器
     */
    @Override
    public void turnOn() {
        setState("on");
    }

    /**
     * 关闭温控器
     */
    @Override
    public void turnOff() {
        setState("off");
    }

    /**
     * 设置目标温度
     * 注意：设置温度会自动打开温控器
     *
     * @param temperature 目标温度（0°C - 40°C之间）
     * @throws IllegalArgumentException 如果温度值不在有效范围内
     */
    public void setTemperature(double temperature) {
        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException(
                    String.format("Invalid temperature: %.1f°C. Must be between %.1f°C and %.1f°C",
                            temperature, MIN_TEMPERATURE, MAX_TEMPERATURE)
            );
        }
        this.temperature = temperature;
    }

    /**
     * 获取当前目标温度
     *
     * @return 当前目标温度（摄氏度）
     */
    public double getTemperature() {
        return this.temperature;
    }

    /**
     * 返回温控器设备的字符串表示
     * 包含名称、类型、状态和温度信息
     *
     * @return 格式化的设备信息字符串
     */
    @Override
    public String toString() {
        return String.format("SmartDevice{name='%s', type='%s', state='%s', Temperature=%.1f}",
                this.getName(), this.getType(), this.getState(), this.temperature);
    }
}