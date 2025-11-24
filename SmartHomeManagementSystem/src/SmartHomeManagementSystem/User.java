package SmartHomeManagementSystem;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class User {
    public String username;
    public SmartHomeHub hub;


    public User(String username, SmartHomeHub hub) {
        this.username = username;
        this.hub = hub;
    }

    public User(String username) {
        this.username = username;
        this.hub = new SmartHomeHub();
    }

    public User() {
        this.username = null;
        this.hub = new SmartHomeHub();
    }

    public void viewAllDevices()
    {
        this.hub.viewAllDevices();
    }

    public void controlDevices(String name,String state)
    {
        this.hub.controlDevice(name, state);
    }

    public void createGroup(String name, List<SmartDevice> devices)
    {
        DeviceGroup newGroups = new DeviceGroup(name,devices);
    }

    //add devices to hub
    public void addDeviceToHub(String device_name){
        this.hub.addDevice(device_name);
    }

    //delete devices to hub
    public void removeDeviceFromHub(String device_name){
        this.hub.removeDevice(device_name);
    }

    //
    public void addGroupToHub(String group_name){
        this.hub.addGroup(group_name);
    }
    public void removeGroupFromHub(String group_name){
        this.hub.removeGroup(group_name);
    }

    // pass the desired state using device_states map
    public Scene createScene(String name, Map<String,String> device_states)
    {
        Scene newScene = new Scene(name,device_states);
        this.hub.addScene(newScene);
        return newScene;
    }

    // not listed in the uml but I think we should be able to modify the scenes
    public void modifyScene(String name, Map<String,String> device_states)
    {

    }

    public void runScene(String name)
    {
        this.hub.executeScene(name);
    }


}
