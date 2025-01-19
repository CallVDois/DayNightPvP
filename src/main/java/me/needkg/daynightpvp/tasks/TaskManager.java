package me.needkg.daynightpvp.tasks;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.configuration.manager.GlobalConfigurationManager;
import me.needkg.daynightpvp.configuration.manager.WorldConfigurationManager;
import me.needkg.daynightpvp.utils.LoggingUtil;
import me.needkg.daynightpvp.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private final GlobalConfigurationManager globalConfigurationManager;
    private final WorldConfigurationManager worldConfigurationManager;
    private final List<Integer> scheduledTasks;
    private final List<BossBar> activeBossBars;
    private final Map<String, TimeDurationController> worldTimeControllers;

    public TaskManager(GlobalConfigurationManager globalConfigurationManager, WorldConfigurationManager worldConfigurationManager) {
        this.globalConfigurationManager = globalConfigurationManager;
        this.worldConfigurationManager = worldConfigurationManager;
        this.scheduledTasks = new ArrayList<>();
        this.activeBossBars = new ArrayList<>();
        this.worldTimeControllers = new HashMap<>();
    }

    public void startAllTasks() {
        for (String worldName : globalConfigurationManager.getWorldNames()) {
            if (WorldUtil.isWorldValid(worldName)) {
                initializeWorldTasks(worldName);
            }
        }
    }

    private void initializeWorldTasks(String worldName) {
        World world = Bukkit.getWorld(worldName);
        TimeDurationController timeDurationController = null;

        if (worldConfigurationManager.isDayNightDurationEnabled(worldName)) {
            LoggingUtil.sendVerboseMessage("Initializing the TimeDurationController task...");
            timeDurationController = initializeTimeDurationController(worldName, world);
        }

        if (worldConfigurationManager.isBossbarEnabled(worldName)) {
            LoggingUtil.sendVerboseMessage("Initializing the BossBar task...");
            initializeBossBar(worldName, world, timeDurationController);
        }

        if (worldConfigurationManager.isPvpAutomaticEnabled(worldName)) {
            LoggingUtil.sendVerboseMessage("Initializing the WorldStateController task...");
            initializeWorldStateController(worldName, world);
        }
    }

    private TimeDurationController initializeTimeDurationController(String worldName, World world) {
        TimeDurationController timeDurationController = new TimeDurationController(world, worldName);

        scheduledTasks.add(scheduleRepeatingTask(timeDurationController, 1));
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        worldTimeControllers.put(worldName, timeDurationController);

        return timeDurationController;
    }

    private void initializeBossBar(String worldName, World world, TimeDurationController timeDurationController) {
        BossBar bossBar = Bukkit.createBossBar("bossbar", BarColor.BLUE, BarStyle.SOLID);
        activeBossBars.add(bossBar);

        TimeBossBar bossBarTask = new TimeBossBar(bossBar, world, worldName, timeDurationController);

        scheduledTasks.add(scheduleRepeatingTask(bossBarTask, 20));
    }

    private void initializeWorldStateController(String worldName, World world) {
        WorldStateController pvpTask = new WorldStateController(world, worldName);

        scheduledTasks.add(scheduleRepeatingTask(pvpTask, 20));
    }

    private int scheduleRepeatingTask(Runnable task, int tickInterval) {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DayNightPvP.getInstance(), task, 0, tickInterval);
    }

    public void stopAllTasks() {
        cancelAllTasks();
        clearWorldStates();
        removeBossBars();
        restoreWorldSettings();
    }

    private void cancelAllTasks() {
        scheduledTasks.forEach(Bukkit.getScheduler()::cancelTask);
        scheduledTasks.clear();
        worldTimeControllers.clear();
    }

    private void clearWorldStates() {
        WorldStateController.dayWorlds.clear();
        WorldStateController.nightWorlds.clear();
    }

    private void removeBossBars() {
        activeBossBars.forEach(BossBar::removeAll);
        activeBossBars.clear();
    }

    private void restoreWorldSettings() {
        globalConfigurationManager.getValidWorlds().forEach(worldName ->
                Bukkit.getWorld(worldName).setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
    }

    public void restart() {
        stopAllTasks();
        startAllTasks();
    }
}
