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

        SmartDevice gateLocker = new SmartLock("gate locker");
        SmartDevice gateLocker2 = new SmartLock("gate locker2");
        ArrayList<SmartDevice> devices = new ArrayList<>(List.of(upstairsLight, airConditioner, gateLocker));
        ArrayList<SmartDevice> devices2 = new ArrayList<>(List.of(airConditioner, airConditioner2));
        user.addDeviceToHub(upstairsLight);
        user.addDeviceToHub(gateLocker);
        user.addDeviceToHub(gateLocker2);
        user.addDeviceToHub(airConditioner);
        user.viewDeviceState("air conditioner");
        user.controlTemperature("air conditioner", 30.0);
        user.viewDeviceState("air conditioner");
        user.turnOffDevice("air conditioner");
        user.viewDeviceState("air conditioner");
        user.createGroup("test", devices2);
        user.viewDeviceState("test");
        user.turnOffGroup("test");
        user.viewDeviceState("test");
        user.viewDeviceState("air conditioner");
        user.viewDeviceState("air conditioner2");
        user.controlBrightness("upstairs light", 99);
        user.viewDeviceState("upstairs light");
        user.turnOffDevice("upstairs light");
        user.viewDeviceState("upstairs light");
        //user.viewDeviceState("upstairs light");
        user.viewDeviceState("test");
        user.viewAllGroups();
        user.viewAllDevices();
        user.turnOnGroup("test");
        user.viewDeviceState("test");
        user.manageGroupTemperature("test", 12.0);
        user.viewDeviceState("test");
        user.viewDeviceState("air conditioner");
        user.viewDeviceState("gate locker");
        user.unlockDevice("gate locker");
        user.viewDeviceState("gate locker");
        user.unlockDevice("upstairs light");
        user.viewDeviceState(gateLocker);
        ArrayList<String> devicesNames = new ArrayList<>();
        //devicesNames.add("upstairs light");
        devicesNames.add("gate locker");
        user.groupDevicesInHub("test2",devicesNames);
        user.lockGroup("test2");
        user.addMemberToGroup("gate locker2","test2");
        user.viewDeviceState("test2");
        user.removeGroupFromHub("test2");
        user.groupDevicesInHub("test2",devicesNames);
        user.createGroup("test3", devices2);
        user.removeMemberFromGroup("gate locker2","test2");
        user.addMemberToGroup("gate locker2","test2");
        user.viewDeviceState("test2");
        //user.unlockGroup("test2");
        //user.lockGroup("test");
    }
}