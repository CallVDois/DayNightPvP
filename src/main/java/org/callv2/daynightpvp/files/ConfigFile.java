package org.callv2.daynightpvp.files;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.utils.ConsoleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {

    public static File fileLocation;
    public static FileConfiguration fileContent;

    public void createFile() {
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml");
        if (!fileLocation.exists()) {
            DayNightPvP.getInstance().saveResource("config.yml", false);
        }
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        verifyFileVersion();
    }

    private void verifyFileVersion() {
        int latestFileVersion = 17;
        if (latestFileVersion != getVersion()) {
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "config.yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The \"config.yml\" file was outdated and has been renamed to \"config.yml.old\".";
                ConsoleUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the \"config.yml\" file.";
                ConsoleUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        }
    }

    public void setValue(String path, Object value) {
        fileContent.set(path, value);
        saveConfig();
    }

    private void saveConfig() {
        try {
            fileContent.save(fileLocation);
        } catch (Exception e) {
            ConsoleUtils.sendWarningMessage("[DayNightPvP] Error saving configuration file, resetting...");
            resetFile();
        }
    }

    public void resetFile() {
        DayNightPvP.getInstance().saveResource("config.yml", true);
    }

    private void resetValueToDefault(String path, Object value) {
        setValue(path, value);
        String configName = path.replace(".", "/");
        ConsoleUtils.sendWarningMessage("[DayNightPvP] The \"" + configName + "\" configuration was set incorrectly and has been reset.");
    }

    private int getInt(String path, Integer defaultValue, Integer minValue, Integer maxValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        try {
            int intValue = Integer.parseInt(configValue);

            if (intValue >= minValue && intValue <= maxValue) {
                return intValue;
            } else {
                resetValueToDefault(path, defaultValue);
                return defaultValue;
            }
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    private float getFloat(String path, Float defaultValue, Float minValue, Float maxValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        try {
            float floatValue = Float.parseFloat(configValue);

            if (floatValue >= minValue && floatValue <= maxValue) {
                return floatValue;
            } else {
                resetValueToDefault(path, defaultValue);
                return defaultValue;
            }
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    private Difficulty getDifficulty(String path, Difficulty defaultValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        try {
            return Difficulty.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    private Sound getSound(String path, Sound defaultValue) {

        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        try {
            return Sound.valueOf(configValue.toUpperCase());
        } catch (Exception e) {
            resetValueToDefault(path, defaultValue.name());
            return defaultValue;
        }
    }

    private String getString(String path, String defaultValue) {
        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        return configValue;
    }

    private boolean getBoolean(String path, Boolean defaultValue) {
        String configValue = fileContent.getString(path);

        if (configValue == null) {
            resetFile();
            return defaultValue;
        }

        if (configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(configValue);
        } else {
            resetValueToDefault(path, defaultValue);
            return defaultValue;
        }
    }

    private List<World> getWorldList(String path, List<String> defaultValue) {

        List<String> configValue = fileContent.getStringList(path);
        List<World> worldList = new ArrayList<>();

        if (configValue.isEmpty()) {
            resetFile();

            for (String worldName : defaultValue) {
                if (Bukkit.getWorld(worldName) != null) {
                    worldList.add(Bukkit.getWorld(worldName));
                }
            }
            resetValueToDefault(path, defaultValue);
            return worldList;
        }

        for (String worldName : configValue) {
            if (Bukkit.getWorld(worldName) != null) {
                worldList.add(Bukkit.getWorld(worldName));
            }
        }
        return worldList;
    }

    public int getVersion() {
        return fileContent.getInt("version");
    }

    public boolean getUpdateChecker() {
        return getBoolean("update-checker", true);
    }

    public String getLanguage() {
        return getString("language", "en-US");
    }

    public Boolean getDayNightDurationEnabled() {
        return getBoolean("day-night-duration.enabled", false);
    }

    public int getDayNightDurationDayDuration() {
        return getInt("day-night-duration.day-duration", 600, 1, 2147483647);
    }

    public int getDayNightDurationNightDuration() {
        return getInt("day-night-duration.night-duration", 600, 1, 2147483647);
    }

    public List<World> getDayNightDurationWorlds() {
        List<String> defaultValue = new ArrayList<>();
        defaultValue.add("world");
        defaultValue.add("worldNameExample");
        defaultValue.add("MiningWorld");
        return getWorldList("day-night-duration.worlds", defaultValue);
    }

    public List<World> getDayNightPvpWorlds() {
        List<String> defaultValue = new ArrayList<>();
        defaultValue.add("world");
        defaultValue.add("worldNameExample");
        defaultValue.add("MiningWorld");
        return getWorldList("automatic-pvp.worlds", defaultValue);
    }

    public int getDayNightPvpDayEnd() {
        return getInt("automatic-pvp.day-end", 12000, 1, 24000);
    }

    public boolean getDayNightPvpAutomaticDifficultyEnabled() {
        return getBoolean("automatic-difficulty.enabled", false);
    }

    public Difficulty getDayNightPvpAutomaticDifficultyDay() {
        return getDifficulty("automatic-difficulty.day", Difficulty.NORMAL);
    }

    public Difficulty getDayNightPvpAutomaticDifficultyNight() {
        return getDifficulty("automatic-difficulty.night", Difficulty.HARD);
    }

    public boolean getNotifyPlayersChatDayNightStarts() {
        return getBoolean("notify-players.chat.day-night-starts", true);
    }

    public boolean getNotifyPlayersChatHitAnotherPlayerDuringTheDay() {
        return getBoolean("notify-players.chat.hit-another-player-during-the-day", true);
    }

    public boolean getNotifyPlayersTitleEnabled() {
        return getBoolean("notify-players.title.enabled", true);
    }

    public int getNotifyPlayersTitleFadeIn() {
        return getInt("notify-players.title.fade-in", 20, 0, 2147483647);
    }

    public int getNotifyPlayersTitleStay() {
        return getInt("notify-players.title.stay", 20, 0, 2147483647);
    }

    public int getNotifyPlayersTitleFadeOut() {
        return getInt("notify-players.title.fade-out", 20, 0, 2147483647);
    }

    public boolean getNotifyPlayersSoundEnabled() {
        return getBoolean("notify-players.sound.enabled", true);
    }

    public Sound getNotifyPlayersSoundDaySound() {
        return getSound("notify-players.sound.day.sound", Sound.ENTITY_CHICKEN_AMBIENT);
    }

    public float getNotifyPlayersSoundDayVolume() {
        return getFloat("notify-players.sound.day.volume", 1.0f, 0.0f, 1.0f);
    }

    public float getNotifyPlayersSoundNightVolume() {
        return getFloat("notify-players.sound.night.volume", 1.0f, 0.0f, 1.0f);
    }

    public Sound getNotifyPlayersSoundNightSound() {
        return getSound("notify-players.sound.night.sound", Sound.ENTITY_GHAST_AMBIENT);
    }

    public boolean getPvpKeepInventoryWhenKilledByPlayer() {
        return getBoolean("pvp.keep-inventory-when-killed-by-player", false);
    }

    public boolean getVaultLoseMoneyOnDeathEnabled() {
        return getBoolean("vault.lose-money-on-death.enabled", false);
    }

    public boolean getVaultLoseMoneyOnDeathOnlyAtNight() {
        return getBoolean("vault.lose-money-on-death.only-at-night", true);
    }

    public boolean getVaultLoseMoneyOnDeathOnlyInConfiguredWorlds() {
        return getBoolean("vault.lose-money-on-death.only-in-configured-worlds", true);
    }

    public boolean getVaultLoseMoneyOnDeathKillerRewardMoney() {
        return getBoolean("vault.lose-money-on-death.killer-reward-money", true);
    }

    public boolean getGriefPreventionPvpInLandEnabled() {
        return getBoolean("griefprevention.pvp-in-land", false);
    }
}
