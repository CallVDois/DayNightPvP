package me.needkg.daynightpvp.commands.subcommands.validators;

import me.needkg.daynightpvp.commands.subcommands.core.CommandValidator;
import me.needkg.daynightpvp.configuration.file.ConfigurationFile;
import me.needkg.daynightpvp.configuration.manager.MessageManager;
import me.needkg.daynightpvp.configuration.type.MessageType;

import org.bukkit.command.CommandSender;

public class WorldConfiguredValidator implements CommandValidator {
    private final MessageManager messageManager;
    private final ConfigurationFile configurationFile;
    private final boolean shouldBeConfigured;

    public WorldConfiguredValidator(ConfigurationFile configurationFile, MessageManager messageManager, boolean shouldBeConfigured) {
        this.messageManager = messageManager;
        this.configurationFile = configurationFile;
        this.shouldBeConfigured = shouldBeConfigured;
    }

    @Override
    public boolean validate(CommandSender sender, String[] args) {
        if (args.length < 2) return false;
        boolean isConfigured = configurationFile.hasPath("worlds." + args[1]);
        return shouldBeConfigured ? isConfigured : !isConfigured;
    }

    @Override
    public String getErrorMessage(CommandSender sender, String[] args) {
        return shouldBeConfigured 
            ? messageManager.getMessage(MessageType.WORLD_EDITOR_ERROR_NOT_CONFIGURED).replace("{0}", args[1])
            : messageManager.getMessage(MessageType.WORLD_EDITOR_ERROR_ALREADY_EXISTS).replace("{0}", args[1]);
    }
} 