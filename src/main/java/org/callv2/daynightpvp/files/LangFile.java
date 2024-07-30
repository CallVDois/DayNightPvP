package org.callv2.daynightpvp.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.callv2.daynightpvp.DayNightPvP;
import org.callv2.daynightpvp.utils.ConsoleUtils;

import java.io.File;

public class LangFile {

    private static File fileLocation;
    private static FileConfiguration fileContent;
    private final ConfigFile configFile;

    public LangFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public void createFile() {
        String filePath = "lang/" + configFile.getLanguage() + ".yml";
        fileLocation = new File(DayNightPvP.getInstance().getDataFolder(), filePath);

        if (!fileLocation.exists()) {
            DayNightPvP.getInstance().saveResource(filePath, false);
        }

        loadFileContent();
        verifyFileVersion();
    }

    private void verifyFileVersion() {
        int latestFileVersion = 14;
        if (latestFileVersion != getVersion()) {
            File outdatedFile = new File(DayNightPvP.getInstance().getDataFolder(), "lang/" + configFile.getLanguage() + ".yml.old");
            if (outdatedFile.exists()) {
                outdatedFile.delete();
            }
            boolean success = fileLocation.renameTo(outdatedFile);
            if (success) {
                String fileRenamed = "[DayNightPvP] The \"lang/" + configFile.getLanguage() + "\".yml file was outdated and has been renamed to \"lang/" + configFile.getLanguage() + "\".yml.old\".";
                ConsoleUtils.sendWarningMessage(fileRenamed);
            } else {
                String fileRenameFailed = "[DayNightPvP] Failed to rename the \"lang/" + configFile.getLanguage() + "\".yml file.";
                ConsoleUtils.sendWarningMessage(fileRenameFailed);
            }

            resetFile();
            fileContent = YamlConfiguration.loadConfiguration(fileLocation);
        }
    }

    private void resetFile() {
        DayNightPvP.getInstance().saveResource("lang/" + configFile.getLanguage() + ".yml", true);
    }

    private void loadFileContent() {
        fileContent = YamlConfiguration.loadConfiguration(fileLocation);
    }

    private String formatMessage(String path) {
        String text = fileContent.getString(path);
        if (text != null) {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
        return "Invalid message syntax";
    }

    // Version
    public int getVersion() {
        return fileContent.getInt("version");
    }

    // Notificações
    public String getNotifyDayChat() {
        return formatMessage("notify-day-chat");
    }

    public String getNotifyNightChat() {
        return formatMessage("notify-night-chat");
    }

    public String getNotifyDayTitle() {
        return formatMessage("notify-day-title");
    }

    public String getNotifyDaySubtitle() {
        return formatMessage("notify-day-subtitle");
    }

    public String getNotifyNightTitle() {
        return formatMessage("notify-night-title");
    }

    public String getNotifyNightSubtitle() {
        return formatMessage("notify-night-subtitle");
    }

    public String getNotifyPvpDisabled() {
        return formatMessage("notify-pvp-disabled");
    }

    public String getNotifyPlayerImmune() {
        return formatMessage("notify-player-immune");
    }

    public String getNotifySelfImmune() {
        return formatMessage("notify-self-immune");
    }

    // Feedback
    public String getFeedbackUpdateAvailable() {
        return formatMessage("feedback-update-available");
    }

    public String getFeedbackUpdateCurrentVersion() {
        return formatMessage("feedback-update-current-version");
    }

    public String getFeedbackUpdateLatestVersion() {
        return formatMessage("feedback-update-latest-version");
    }

    public String getFeedbackUpdateCheckFailed() {
        return formatMessage("feedback-update-check-failed");
    }

    public String getFeedbackReloadPlugin() {
        return formatMessage("feedback-reload-plugin");
    }

    public String getFeedbackLoseMoney() {
        return formatMessage("feedback-lose-money");
    }

    public String getFeedbackWinMoney() {
        return formatMessage("feedback-win-money");
    }

    public String getFeedbackNonExistentCommand() {
        return formatMessage("feedback-non-existent-command");
    }

    public String getFeedbackError() {
        return formatMessage("feedback-error");
    }

    // Ações
    public String getActionUpdateFoundClick() {
        return formatMessage("action-update-found-click");
    }

    // Placeholder
    public String getPlaceholderPvpEnabled() {
        return formatMessage("placeholder-pvp-enabled");
    }

    public String getPlaceholderPvpDisabled() {
        return formatMessage("placeholder-pvp-disabled");
    }

}
