package org.callvdois.daynightpvp;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.callvdois.daynightpvp.commands.CommandHandler;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.events.EventsManager;
import org.callvdois.daynightpvp.metrics.MetricsManager;
import org.callvdois.daynightpvp.placeholder.PlaceholderManager;
import org.callvdois.daynightpvp.service.ServiceManager;
import org.callvdois.daynightpvp.utils.ConsoleUtils;
import org.callvdois.daynightpvp.utils.PluginUtils;
import org.callvdois.daynightpvp.worldguard.WorldGuardManager;

import java.util.ArrayList;
import java.util.List;

public class DayNightPvP extends JavaPlugin {

    public static boolean vaultIsPresent;
    public static boolean griefIsPresent;
    public static boolean worldGuardIsPresent;
    public static boolean placeHolderIsPresent;
    public static List<BukkitTask> serviceTasks = new ArrayList<>();
    private static DayNightPvP instance;
    private final FilesManager filesManager;
    private final CommandHandler commandHandler;
    private final EventsManager eventsManager;
    private final PlaceholderManager placeholderManager;
    private final WorldGuardManager worldGuardManager;
    private final ServiceManager serviceManager;
    private final MetricsManager metricsManager;

    public DayNightPvP() {
        instance = this;

        filesManager = new FilesManager();
        commandHandler = new CommandHandler();
        eventsManager = new EventsManager();
        placeholderManager = new PlaceholderManager();
        worldGuardManager = new WorldGuardManager();
        serviceManager = new ServiceManager();
        metricsManager = new MetricsManager();

    }

    public static DayNightPvP getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    private void load() {

        verifyCompatibilityPlugins();

        if (worldGuardIsPresent) {
            worldGuardManager.register();
        }
    }

    private void enable() {
        ConsoleUtils.startMessage();

        filesManager.createFiles();

        commandHandler.register();

        eventsManager.register();

        placeholderManager.register();

        serviceManager.startServices();

        metricsManager.register();
    }

    private void verifyCompatibilityPlugins() {
        vaultIsPresent = PluginUtils.isPluginInstalled("Vault");
        worldGuardIsPresent = PluginUtils.isPluginInstalled("WorldGuard");
        griefIsPresent = PluginUtils.isPluginInstalled("GriefPrevention");
        placeHolderIsPresent = PluginUtils.isPluginInstalled("PlaceholderAPI");
    }

    @Override
    public void onDisable() {
        serviceManager.stopServices();
    }

}