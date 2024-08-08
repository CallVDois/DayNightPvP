package org.callv2.daynightpvp.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.callv2.daynightpvp.commands.ISubCommand;
import org.callv2.daynightpvp.files.ConfigFile;
import org.callv2.daynightpvp.files.LangFile;

import java.util.ArrayList;
import java.util.List;

public class DelWorldSubCommand implements ISubCommand {

    private final LangFile langFile;
    private final ConfigFile configFile;

    public DelWorldSubCommand(LangFile langFile, ConfigFile configFile) {
        this.langFile = langFile;
        this.configFile = configFile;
    }

    @Override
    public void executeCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 2) {
            if (configFile.contains("worlds." + args[1])) {
                removeWorldFromConfig(args[1]);
                sender.sendMessage(langFile.getFeedbackDeletedWorld().replace("{0}", args[1]));
                return;
            }
            sender.sendMessage(langFile.getFeedbackWorldIsNotInSettings().replace("{0}", args[1]));
        } else {
            sender.sendMessage(langFile.getFeedbackIncorrectCommand().replace("{0}", "Comando incorreto, use: /dnp delworld <worldName>"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, List<String> args) {
        List<String> suggestions = new ArrayList<>();

        if (args.size() == 1) {
            String prefix = args.get(0).toLowerCase(); // Obtenha o prefixo digitado
            for (String worldName : configFile.getWorlds()) {
                if (worldName.startsWith(prefix)) {
                    suggestions.add(worldName);
                }
            }
        }
        return suggestions;
    }

    private void removeWorldFromConfig(String worldName) {
        if (configFile.contains("worlds." + worldName)) {
            configFile.setValue("worlds." + worldName, null);
            configFile.saveConfig();
        }
    }

}
