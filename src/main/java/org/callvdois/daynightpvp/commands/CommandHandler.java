package org.callvdois.daynightpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.callvdois.daynightpvp.DayNightPvP;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandHandler() {
        subCommands.put("reload", new DnpReload());
    }

    public void register() {
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(this);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Mostra todos os comandos.");
            return false;
        }

        SubCommand subCommand = subCommands.get(args[0]);

        if (subCommand == null) {
            sender.sendMessage("Subcomando não encontrado!");
            return false;
        }

        subCommand.execute(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return Collections.emptyList();
    }
}
