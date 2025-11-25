package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能门锁类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供门锁特定的功能
 *
 */
public class SmartLock extends SmartDevice {
    private boolean isLocked;
    private static ArrayList<String> legal_states = new ArrayList<>(List.of("locked","unlocked"));

    /**
     * 构造方法
     * 默认状态为锁定
     *
     * @param name 门锁设备名称
     */
    public SmartLock(String name) {
        super(name, "Lock");
        this.isLocked = true;
        setState("locked");
    }

    /**
     * 设置门锁状态
     *
     * @param state 要设置的状态（locked/unlocked/on/off）
     * @throws IllegalArgumentException 如果状态不合法
     */
    @Override
    public void setState(String state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }

        String lowerState = state.toLowerCase();

        // 支持多种状态表示方式
        if (lowerState.equals("on")) {
            lowerState = "unlocked";
        } else if (lowerState.equals("off")) {
            lowerState = "locked";
        }

        // 验证状态是否合法
        if (!legal_states.contains(lowerState)) {
            throw new IllegalArgumentException("Invalid state for SmartLock: " + state + ". Must be 'locked', 'unlocked', 'on', or 'off'");
        }

        // 更新状态和isLocked标志
        super.setState(lowerState);
        this.isLocked = lowerState.equals("locked");
    }

    /**
     * 锁定门锁
     */
    public void lock() {
        setState("locked");
    }

    /**
     * 解锁门锁
     */
    public void unlock() {
        setState("unlocked");
    }

    /**
     * 打开（解锁）门锁
     */
    @Override
    public void turnOn() {
        unlock();
    }

    /**
     * 关闭（锁定）门锁
     */
    @Override
    public void turnOff() {
        lock();
    }

    /**
     * 检查门锁是否处于锁定状态
     *
     * @return 如果门锁已锁定返回true，否则返回false
     */
    public boolean isLocked() {
        return this.isLocked;
    }
}