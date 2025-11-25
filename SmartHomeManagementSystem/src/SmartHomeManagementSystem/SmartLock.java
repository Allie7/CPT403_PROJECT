package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能门锁类 - SmartDevice的具体实现
 * 继承自抽象类SmartDevice，提供门锁特定的功能
 */
public class SmartLock extends SmartDevice {

    private boolean isLocked; // kind of redundant, may be we should delete it
    private static ArrayList<String> legalStates = new ArrayList<>(List.of("lock","unlock"));

    /**
     *
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
        lock();
    }


    @Override
    public void turnOff() {
        unlock();
    }


    public boolean isLocked() {
        return state.equals("locked");
    }

    public void setState(String state) {
        state = state.toLowerCase();
        if (state.equals("on")){
            this.state = "unlocked";
        }
        else if (state.equals("off")){
            this.state = "locked";
        }else if (legalStates.contains(state)){
            this.state = "locked";
        }
    }


}
