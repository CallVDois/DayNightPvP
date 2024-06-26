package org.callvdois.daynightpvp.service;

import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.callvdois.daynightpvp.config.ConfigManager;
import org.callvdois.daynightpvp.config.LangManager;
import org.callvdois.daynightpvp.utils.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeCheckerService extends BukkitRunnable {

    public static List<World> worldsPvpOff = new ArrayList<>();
    public static List<World> worldsPvpOn = new ArrayList<>();
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final PlayerUtils playerUtils;

    public TimeCheckerService() {
        configManager = new ConfigManager();
        langManager = new LangManager();
        playerUtils = new PlayerUtils();
    }

    @Override
    public void run() {
        List<World> worldList = configManager.getDayNightPvpWorlds();
        for (World world : worldList) {
            if (checkTime(world)) {
                handleNight(world);
            } else {
                handleDay(world);
            }
        }
    }

    private void handleNight(World world) {
        if (!worldsPvpOn.contains(world)) {
            worldsPvpOn.add(world);
            //ConsoleUtils.info("[DayNightPvP] It's night in \"" + world.getName() + "\"");
        }
        worldsPvpOff.remove(world);
    }

    private void handleDay(World world) {
        if (!worldsPvpOff.contains(world)) {
            worldsPvpOff.add(world);
            //ConsoleUtils.info("[DayNightPvP] It's day in \"" + world.getName() + "\"");
        }
        worldsPvpOn.remove(world);
    }

    public boolean checkTime(World world) {
        long currentWorldTime = world.getTime();
        if (currentWorldTime < configManager.getDayNightPvpDayEnd()) {
            if (!worldsPvpOff.contains(world)) {
                if (configManager.getDayNightPvpAutomaticDifficultyEnabled()) {
                    world.setDifficulty(configManager.getDayNightPvpAutomaticDifficultyDay());
                }
                if (configManager.getNotifyPlayersChatDayNightStarts()) {
                    PlayerUtils.sendMessageToAllPlayers(world, langManager.getNotifyDayChat());
                }
                if (configManager.getNotifyPlayersTitleEnabled()) {
                    playerUtils.sendTitleToAllPlayers(world, langManager.getNotifyDayTitle(), langManager.getNotifyDaySubtitle());
                }
                if (configManager.getNotifyPlayersSoundEnabled()) {
                    PlayerUtils.playSoundToAllPlayers(world, configManager.getNotifyPlayersSoundDay());
                }
            }
            return false;
        } else {
            if (!worldsPvpOn.contains(world)) {
                if (configManager.getDayNightPvpAutomaticDifficultyEnabled()) {
                    world.setDifficulty(configManager.getDayNightPvpAutomaticDifficultyNight());
                }
                if (configManager.getNotifyPlayersChatDayNightStarts()) {
                    PlayerUtils.sendMessageToAllPlayers(world, langManager.getNotifyNightChat());
                }
                if (configManager.getNotifyPlayersTitleEnabled()) {
                    playerUtils.sendTitleToAllPlayers(world, langManager.getNotifyNightTitle(), langManager.getNotifyNightSubtitle());
                }
                if (configManager.getNotifyPlayersSoundEnabled()) {
                    PlayerUtils.playSoundToAllPlayers(world, configManager.getNotifyPlayersSoundNight());
                }
            }
            return true;
        }
    }

}