package me.needkg.daynightpvp.utils;

import me.needkg.daynightpvp.DayNightPvP;
import me.needkg.daynightpvp.core.DependencyContainer;
import org.bukkit.Bukkit;

public class LoggingUtil {

    public static void sendWarningMessage(String message) {
        Bukkit.getLogger().warning(message);
    }

    public static void sendInfoMessage(String message) {
        Bukkit.getLogger().info(message);
    }

    public static void sendDebugMessage(String message) {
        if (DependencyContainer.getInstance().getGlobalConfigurationManager().isDebugEnabled()) {
            Bukkit.getLogger().info("[DayNightPvP Debug] " + message);
        }
    }

    public static void sendVerboseMessage(String message) {
        if (DependencyContainer.getInstance().getGlobalConfigurationManager().isDebugEnabled() && DependencyContainer.getInstance().getGlobalConfigurationManager().isDebugVerbose()) {
            Bukkit.getLogger().info("[DayNightPvP Verbose] " + message);
        }
    }

    public static void sendStartupMessage() {
        Bukkit.getConsoleSender().sendMessage("   §9 _     _");
        Bukkit.getConsoleSender().sendMessage("   §9| \\|\\||_)" + "   §3DayNightPvP §8v" + DayNightPvP.getInstance().getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("   §9|_/| ||" + "         §8by §3needkg");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
