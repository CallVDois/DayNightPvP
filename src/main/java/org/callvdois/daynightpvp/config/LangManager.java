package org.callvdois.daynightpvp.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.callvdois.daynightpvp.DayNightPvP;

public class LangManager {

    public static FileConfiguration fileConfiguration;
    private final ConfigManager configManager;

    public LangManager() {
        configManager = new ConfigManager();
    }

    public void getLanguageFileSelected() {
        String pathLangFile = "lang/" + configManager.getLanguage() + ".yml";
        fileConfiguration = FilesManager.loadConfigFile(DayNightPvP.getInstance(), pathLangFile);
    }

    private String formatMessage(String path) {
        String text = fileConfiguration.getString(path);
        if (text != null) {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
        return "Invalid message syntax";
    }

    // Version
    public int getVersion() {
        return fileConfiguration.getInt("version");
    }

    // Estados
    public String getStateEnabled() {
        return formatMessage("state-enabled");
    }

    public String getStateDisabled() {
        return formatMessage("state-disabled");
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

    // Feedback
    public String getFeedbackUpdateFound() {
        return formatMessage("feedback-update-found");
    }

    public String getFeedbackUpdateFoundCurrentVersion() {
        return formatMessage("feedback-update-found-current-version");
    }

    public String getFeedbackUpdateFoundNewVersion() {
        return formatMessage("feedback-update-found-new-version");
    }

    public String getFeedbackReloadPlugin() {
        return formatMessage("feedback-reload-plugin");
    }

    public String getFeedbackSelectLang() {
        return formatMessage("feedback-select-lang");
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

    // Interface
    public String getGuiLanguagesButton() {
        return formatMessage("gui-languages-button");
    }

    public String getGuiLanguagesButtonDescription() {
        return formatMessage("gui-languages-button-description");
    }

    public String getGuiLanguageButtonDescription() {
        return formatMessage("gui-language-button-description");
    }

    public String getGuiReloadButton() {
        return formatMessage("gui-reload-button");
    }

    public String getGuiReloadButtonDescription() {
        return formatMessage("gui-reload-button-description");
    }

    public String getGuiWorldsButton() {
        return formatMessage("gui-worlds-button");
    }

    public String getGuiWorldsButtonDescription() {
        return formatMessage("gui-worlds-button-description");
    }

    public String getGuiDayButton() {
        return formatMessage("gui-day-button");
    }

    public String getGuiDayButtonDescription() {
        return formatMessage("gui-day-button-description");
    }

    public String getGuiNightButton() {
        return formatMessage("gui-night-button");
    }

    public String getGuiNightButtonDescription() {
        return formatMessage("gui-night-button-description");
    }

    public String getGuiWorldButtonDescriptionDayNightPvp() {
        return formatMessage("gui-world-button-description-daynightpvp");
    }

    public String getGuiWorldButtonDescriptionType() {
        return formatMessage("gui-world-button-description-type");
    }

    public String getGuiWorldButtonDescriptionTime() {
        return formatMessage("gui-world-button-description-time");
    }

    public String getGuiWorldButtonDescriptionDay() {
        return formatMessage("gui-world-button-description-day");
    }

    public String getGuiWorldButtonDescriptionNight() {
        return formatMessage("gui-world-button-description-night");
    }

    public String getGuiWorldButtonDescriptionNotSupported() {
        return formatMessage("gui-world-button-description-not-supported");
    }

    public String getGuiDayNightPvpButton() {
        return formatMessage("gui-daynightpvp-button");
    }

    public String getGuiBackButton() {
        return formatMessage("gui-back-button");
    }

    public String getGuiBackButtonDescription() {
        return formatMessage("gui-back-button-description");
    }

    public String getGuiExitButton() {
        return formatMessage("gui-exit-button");
    }

    public String getGuiExitButtonDescription() {
        return formatMessage("gui-exit-button-description");
    }

    // Ações
    public String getActionUpdateFoundClick() {
        return formatMessage("action-update-found-click");
    }

    public String getActionButtonClickToSeeSettings() {
        return formatMessage("action-button-click-to-see-settings");
    }

    public String getActionButtonClickToDisable() {
        return formatMessage("action-button-click-to-disable");
    }

    public String getActionButtonClickToEnable() {
        return formatMessage("action-button-click-to-enable");
    }

    // Placeholder
    public String getPlaceholderPvpEnabled() {
        return formatMessage("placeholder-pvp-enabled");
    }

    public String getPlaceholderPvpDisabled() {
        return formatMessage("placeholder-pvp-disabled");
    }

}
