package SmartHomeManagementSystem;

/**
 * 智能门锁类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供门锁特定的功能
 */
public class SmartLock extends SmartDevice {
    private boolean isLocked;

    /**
     * 构造方法
     *
     * @param name 门锁名称
     */
    public SmartLock(String name) {
        super(name, "Lock");
        this.isLocked = true;
        this.state = "locked";
        setState("locked");
    }

    /**
     *
     */
    public void lock() {
        setState("locked");
        isLocked = true;
    }

    /**
     * 解锁方法
     */
    public void unlock() {
        setState("unlocked");
        isLocked = false;
    }


    @Override
    public void turnOn() {
        unlock();
        isLocked = false;
    }


    @Override
    public void turnOff() {
        lock();
        isLocked = true;
    }


    public boolean isLocked() {
        return state.equals("locked");
    }



}
