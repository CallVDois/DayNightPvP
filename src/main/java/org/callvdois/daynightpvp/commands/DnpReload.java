package org.callvdois.daynightpvp.commands;

import org.bukkit.command.CommandSender;
import org.callvdois.daynightpvp.config.FilesManager;
import org.callvdois.daynightpvp.config.LangManager;

public class DnpReload extends SubCommand {

    private final LangManager langManager;

    public DnpReload() {
        langManager = new LangManager();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        new FilesManager().reloadPlugin();
        sender.sendMessage(langManager.getFeedbackReloadPlugin());
    }
}
