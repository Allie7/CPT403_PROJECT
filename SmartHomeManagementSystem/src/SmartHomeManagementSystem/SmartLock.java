package SmartHomeManagementSystem;

/**
 * 智能门锁类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供门锁特定的功能
 */
public class SmartLock extends SmartDevice {
    private boolean isLocked;
    private String lockState; // 可以扩展为更多状态：locked, unlocked, jammed等

    /**
     * 构造方法
     *
     * @param name 门锁名称
     */
    public SmartLock(String name) {
        super(name, "Lock");
        this.isLocked = true; // 默认上锁状态
        this.lockState = "locked";
        setState("locked"); // 设置父类的state属性
    }

    /**
     * 上锁方法
     */
    public void lock() {
        if (!isLocked) {
            isLocked = true;
            lockState = "locked";
            setState("locked");
            System.out.println(getName() + " 已上锁");
        } else {
            System.out.println(getName() + " 已经是上锁状态");
        }
    }

    /**
     * 解锁方法
     */
    public void unlock() {
        if (isLocked) {
            isLocked = false;
            lockState = "unlocked";
            setState("unlocked");
            System.out.println(getName() + " 已解锁");
        } else {
            System.out.println(getName() + " 已经是解锁状态");
        }
    }

    /**
     * 实现父类的抽象方法 - 开启设备（等同于解锁）
     */
    @Override
    public void turnOn() {
        unlock();
    }

    /**
     * 实现父类的抽象方法 - 关闭设备（等同于上锁）
     */
    @Override
    public void turnOff() {
        lock();
    }

    /**
     * 检查是否上锁
     *
     * @return 上锁状态
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * 获取详细的锁状态
     *
     * @return 锁状态描述
     */
    public String getLockState() {
        return lockState;
    }

    /**
     * 切换锁状态（上锁/解锁切换）
     */
    public void toggleLock() {
        if (isLocked) {
            unlock();
        } else {
            lock();
        }
    }
}
