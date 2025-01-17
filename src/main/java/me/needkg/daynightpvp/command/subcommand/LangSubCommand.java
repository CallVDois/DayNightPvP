package me.needkg.daynightpvp.command.subcommand;

import me.needkg.daynightpvp.command.subcommand.base.ISubCommand;
import me.needkg.daynightpvp.configuration.ConfigurationManager;
import me.needkg.daynightpvp.configuration.config.GeneralConfiguration;
import me.needkg.daynightpvp.configuration.message.SystemMessages;
import me.needkg.daynightpvp.core.di.DependencyContainer;
import me.needkg.daynightpvp.service.PluginService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LangSubCommand implements ISubCommand {

    private static final List<String> AVAILABLE_LANGUAGES = Arrays.asList("en-US", "pt-BR", "es-ES", "ru-RU");
    private final GeneralConfiguration generalConfiguration;
    private final ConfigurationManager configurationManager;
    private final PluginService pluginService;
    private final SystemMessages systemMessages;

    public LangSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.generalConfiguration = container.getConfigurationContainer().getGeneralConfiguration();
        this.configurationManager = container.getConfigManager();
        this.pluginService = container.getPluginServices();
        this.systemMessages = container.getMessageContainer().getSystem();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!sender.hasPermission("dnp.admin")) {
            sender.sendMessage(systemMessages.getPermissionDeniedMessage());
            return;
        }

        if (args.length == 2) {
            if (AVAILABLE_LANGUAGES.contains(args[1])) {
                String currentLang = generalConfiguration.getLanguage();
                if (currentLang.equals(args[1])) {
                    sender.sendMessage(systemMessages.getLanguageAlreadyInUseMessage());
                    return;
                }

                configurationManager.setValue("language", args[1]);
                pluginService.reloadFiles();
                sender.sendMessage(systemMessages.getLanguageChangedMessage().replace("{0}", args[1]));
            } else {
                sender.sendMessage(systemMessages.getIncorrectCommandMessage().replace("{0}", "/dnp lang <" + String.join("/", AVAILABLE_LANGUAGES) + ">"));
            }
        } else {
            sender.sendMessage(systemMessages.getIncorrectCommandMessage().replace("{0}", "/dnp lang <lang>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        if (!sender.hasPermission("dnp.admin")) {
            return Collections.emptyList();
        }

        if (args.size() == 1) {
            return filterContains(AVAILABLE_LANGUAGES, args.get(0));
        }

        return Collections.emptyList();
    }

}
