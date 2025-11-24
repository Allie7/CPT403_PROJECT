//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;

import SmartHomeManagementSystem.*;
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        User user = new User("testuser");

        SmartDevice upstairsLight = new SmartLight("upstairs light");
        SmartDevice airConditioner = new SmartThermostat("air conditioner");
        SmartDevice airConditioner2 = new SmartThermostat("air conditioner2");

        SmartDevice gateLocker = new SmartThermostat("gate locker");
        ArrayList<SmartDevice> devices = new ArrayList<>(List.of(upstairsLight, airConditioner, gateLocker));
        ArrayList<SmartDevice> devices2 = new ArrayList<>(List.of( airConditioner,airConditioner2));
        user.addDeviceToHub(upstairsLight);
        user.addDeviceToHub(gateLocker);
        user.addDeviceToHub(airConditioner);
        user.viewDeviceState("air conditioner");
        user.controlDevices("air conditioner", 30.0);
        user.viewDeviceState("air conditioner");
        user.turnOffDevice("air conditioner");
        user.viewDeviceState("air conditioner");
        user.createGroup("test",devices2);
        user.viewDeviceState("test");
        user.manageGroup("test","off");
        user.viewDeviceState("test");
        user.viewDeviceState("air conditioner");
        user.viewDeviceState("air conditioner2");
        user.controlDevices("upstairs light", 99);
        user.viewDeviceState("upstairs light");
        user.turnOffDevice("upstairs light");
        user.viewDeviceState("upstairs light");
        user.removeDeviceFromHub("air conditioner2");
        //user.viewDeviceState("upstairs light");
        user.viewDeviceState("test");


    }
}