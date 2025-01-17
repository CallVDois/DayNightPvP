package me.needkg.daynightpvp.command;

import me.needkg.daynightpvp.DayNightPvP;

public class CommandManager {

    public void register() {
        DayNightPvpCommand dayNightPvPCommand = new DayNightPvpCommand();
        DayNightPvP.getInstance().getCommand("daynightpvp").setExecutor(dayNightPvPCommand);
        DayNightPvP.getInstance().getCommand("daynightpvp").setTabCompleter(dayNightPvPCommand);
    }
}
