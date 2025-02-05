package me.needkg.daynightpvp.service.plugin;

import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.file.LanguageFile;
import me.needkg.daynightpvp.integration.placeholder.PlaceholderManager;
import me.needkg.daynightpvp.listener.manager.ListenerManager;
import me.needkg.daynightpvp.tasks.manager.TaskManager;
import me.needkg.daynightpvp.tasks.manager.WorldStateManager;
import me.needkg.daynightpvp.utils.logging.Logger;

public class PluginService {

    private final ConfigurationFile configurationFile;
    private final LanguageFile languageFile;
    private final ListenerManager listenerManager;
    private final PlaceholderManager placeholderManager;
    private final TaskManager taskManager;
    private final WorldStateManager worldStateManager;

    public PluginService(ConfigurationFile configurationFile, LanguageFile languageFile, TaskManager taskManager, ListenerManager listenerManager, PlaceholderManager placeholderManager, WorldStateManager worldStateManager) {
        this.configurationFile = configurationFile;
        this.languageFile = languageFile;
        this.taskManager = taskManager;
        this.listenerManager = listenerManager;
        this.placeholderManager = placeholderManager;
        this.worldStateManager = worldStateManager;
    }

    public void reloadPlugin() {
        Logger.debug("Reloading files...");
        reloadFiles();
        Logger.debug("Restarting scheduled tasks...");
        taskManager.restart();
        Logger.debug("Restarting event handlers...");
        listenerManager.restart();
        Logger.debug("Restarting PlaceholderAPI...");
        placeholderManager.restart();
        Logger.debug("Restarting WorldStateManager...");
        worldStateManager.initializeWorldStates();
    }

    public void reloadFiles() {
        configurationFile.refreshFile();
        languageFile.refreshFile();
    }

}
