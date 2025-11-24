package SmartHomeManagementSystem;

import java.util.ArrayList;
import java.util.List;
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
        hub.viewAllDevices();
    }

    public void viewAllGroups()
    {
        hub.viewAllGroups();
    }

    public void controlDevices(String name,String state)
    {
        hub.controlDevice(name, state);
    }

    /**
     * Allow  light objects to be passed directly a interger value (percentage)
     * @param name
     * @param num
     */
    public void controlDevices(String name,int num)
    {
        hub.controlDevice(name, num);
    }
    public void controlDevices(String name,double num)
    {
        hub.controlDevice(name, num);
    }

    /**
    add & create group
     **/
    public void createGroup(String name, List<SmartDevice> devices)
    {
        String checkType = devices.get(0).getType();
        for(SmartDevice device : devices){
            if (!device.getType().equals(checkType)){
                throw new IllegalArgumentException("The device type does not match");
            }
        }
        DeviceGroup newGroups = new DeviceGroup(name,devices);
        hub.addGroup(newGroups);
    }

    //add devices trhough name
    public void addDeviceToHub(String device_name){
        hub.addDevice(device_name);
    }
    //add
    public void addDeviceToHub(SmartDevice device){
        hub.addDevice(device);
    }

    public void addGroupToHub(String device_name){
        hub.addDevice(device_name);
    }
    //delete devices to hub
    public void removeDeviceFromHub(String device_name){
        hub.removeDevice(device_name);
    }


    public void removeGroupFromHub(String group_name){
        hub.removeGroup(group_name);
    }
    public void removeGroupFromHub(DeviceGroup group){
        hub.removeGroup(group);
    }

    // pass the desired state using device_states map
    public Scene createScene(String name, Map<String, String> device_states)
    {
        Scene newScene = new Scene(name,device_states);
        hub.addScene(newScene);
        return newScene;
    }

    // not listed in the uml but I think we should be able to modify the scenes
    public void modifyScene(String name, Map<String,String> device_states)
    {

    }

    public void runScene(String name)
    {
        hub.executeScene(name);
    }

    /** TURN ON device shortcut
     * @param name
     * @param state
     */
    public void turnOnGroup(String group_name){
        hub.manageGroup(group_name,"ON");
    }

    public void turnOnDevice(String device_name){
        hub.controlDevice(device_name,"ON");
    }


    public void turnOffGroup(String group_name){
        hub.manageGroup(group_name,"OFF");
    }

    public void turnOffDevice(String device_name){
        hub.controlDevice(device_name,"OFF");
    }

    public void lockDevice(String device_name){
        hub.controlDevice(device_name,"LOCKED");
    }

    public void unlockDevice(String device_name){
        hub.controlDevice(device_name,"UNLOCKED");
    }

    public void lockGroup(String group_name){
        hub.manageGroup(group_name,"LOCKED");
    }

    public void unlockGroup(String group_name){
        hub.manageGroup(group_name,"UNLOCKED");
    }


    public void manageGroup(String name,String state)
    {
        hub.manageGroup(name,state);
    }

    /** specified device name, group name or "ALL"
     *
     */
    public void viewDeviceState(String device_name){
        hub.viewDeviceState(device_name);
    }

}
