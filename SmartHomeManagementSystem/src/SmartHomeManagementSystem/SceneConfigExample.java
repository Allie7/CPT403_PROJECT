package SmartHomeManagementSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * 场景配置示例类
 * 提供预定义的常用场景配置
 *
 */
public class SceneConfigExample {

    /**
     * 创建"电影之夜"场景配置
     * - 客厅灯：调暗到20%亮度
     * - 卧室灯：关闭
     * - 前门锁：锁定
     * - 温控器：设置到22°C
     *
     * @return 电影之夜场景的设备状态映射
     */
    public static Map<String, String> getMovieNightConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("客厅灯", "20");      // 亮度20%
        config.put("卧室灯", "off");     // 关闭
        config.put("前门锁", "locked");  // 锁定
        config.put("温控器", "22");      // 22°C
        return config;
    }

    /**
     * 创建"离家模式"场景配置
     * - 所有灯：关闭
     * - 所有锁：锁定
     * - 温控器：设置到节能温度18°C
     *
     * @return 离家模式场景的设备状态映射
     */
    public static Map<String, String> getLeavingHomeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("客厅灯", "off");
        config.put("卧室灯", "off");
        config.put("厨房灯", "off");
        config.put("前门锁", "locked");
        config.put("后门锁", "locked");
        config.put("温控器", "18");
        return config;
    }

    /**
     * 创建"睡眠模式"场景配置
     * - 所有灯：关闭
     * - 所有锁：锁定
     * - 温控器：设置到舒适睡眠温度20°C
     *
     * @return 睡眠模式场景的设备状态映射
     */
    public static Map<String, String> getSleepModeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("客厅灯", "off");
        config.put("卧室灯", "off");
        config.put("前门锁", "locked");
        config.put("后门锁", "locked");
        config.put("温控器", "20");
        return config;
    }

    /**
     * 创建"回家模式"场景配置
     * - 客厅灯：打开到100%亮度
     * - 前门锁：解锁
     * - 温控器：设置到舒适温度23°C
     *
     * @return 回家模式场景的设备状态映射
     */
    public static Map<String, String> getWelcomeHomeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("客厅灯", "100");
        config.put("玄关灯", "on");
        config.put("前门锁", "unlocked");
        config.put("温控器", "23");
        return config;
    }

    /**
     * 创建"派对模式"场景配置
     * - 所有灯：最亮
     * - 所有锁：解锁（方便客人进入）
     * - 温控器：设置到凉爽温度21°C
     *
     * @return 派对模式场景的设备状态映射
     */
    public static Map<String, String> getPartyModeConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("客厅灯", "100");
        config.put("厨房灯", "100");
        config.put("前门锁", "unlocked");
        config.put("温控器", "21");
        return config;
    }

    /**
     * 示例：如何使用这些配置
     */
    public static void main(String[] args) {
        // 创建用户
        User user = new User("Alice");

        // ==================== 步骤1：创建设备并添加到Hub ====================
        System.out.println("=== 创建设备 ===");

        // 创建灯光设备
        SmartLight livingRoomLight = new SmartLight("客厅灯");
        SmartLight bedroomLight = new SmartLight("卧室灯");
        SmartLight kitchenLight = new SmartLight("厨房灯");
        SmartLight entranceLight = new SmartLight("玄关灯");

        // 创建门锁设备
        SmartLock frontDoorLock = new SmartLock("前门锁");
        SmartLock backDoorLock = new SmartLock("后门锁");

        // 创建温控器设备
        SmartThermostat thermostat = new SmartThermostat("温控器");

        // 添加设备到Hub
        user.addDeviceToHub(livingRoomLight);
        user.addDeviceToHub(bedroomLight);
        user.addDeviceToHub(kitchenLight);
        user.addDeviceToHub(entranceLight);
        user.addDeviceToHub(frontDoorLock);
        user.addDeviceToHub(backDoorLock);
        user.addDeviceToHub(thermostat);

        System.out.println("设备创建完成！\n");

        // ==================== 步骤2：使用预定义配置创建场景 ====================
        System.out.println("=== 创建场景 ===");

        Scene movieNight = user.createScene("电影之夜", getMovieNightConfig());
        System.out.println("场景 '电影之夜' 创建成功");

        Scene sleepMode = user.createScene("睡眠模式", getSleepModeConfig());
        System.out.println("场景 '睡眠模式' 创建成功");

        Scene welcomeHome = user.createScene("回家模式", getWelcomeHomeConfig());
        System.out.println("场景 '回家模式' 创建成功\n");

        // ==================== 步骤3：执行场景 ====================
        System.out.println("=== 执行场景：电影之夜 ===");
        user.runScene("电影之夜");

        // 查看设备状态
        System.out.println("\n=== 场景执行后的设备状态 ===");
        user.viewDeviceState("ALL");

        System.out.println("\n场景演示完成！");
    }
}