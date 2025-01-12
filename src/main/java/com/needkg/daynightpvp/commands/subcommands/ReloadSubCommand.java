package com.needkg.daynightpvp.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.needkg.daynightpvp.commands.ISubCommand;
import com.needkg.daynightpvp.di.DependencyContainer;
import com.needkg.daynightpvp.files.LangFile;
import com.needkg.daynightpvp.services.PluginServices;
import com.needkg.daynightpvp.utils.PlayerUtils;

public class ReloadSubCommand implements ISubCommand {

    private final LangFile langFile;
    private final PluginServices pluginServices;

    public ReloadSubCommand() {
        DependencyContainer container = DependencyContainer.getInstance();
        this.langFile = container.getLangFile();
        this.pluginServices = container.getPluginServices();
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "dnp.admin")) {
            PlayerUtils.sendMessage(sender, langFile.getFeedbackError());
            return;
        }

        pluginServices.reloadPlugin();
        PlayerUtils.sendMessage(sender, langFile.getFeedbackReloadPlugin());
    }

}