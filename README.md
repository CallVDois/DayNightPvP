# DayNightPvP

Dynamic Minecraft PvP Plugin for your server!

DayNightPvP is a plugin that adds a unique and thrilling PvP experience to your Minecraft server. With advanced features
and an easy-to-use interface, you can create a dynamic PvP cycle, activated during the night and deactivated during the
day.

# Download

[Spigot](https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/)

[Modrinth](https://modrinth.com/plugin/daynightpvp)

# Support

Need assistance or have a suggestion? Join our Discord community: [click here](https://discord.needkg.com)

# Features

- Automatic PvP:  Forget the need to manually enable or disable PvP. DayNightPvP takes care of this automatically,
  activating exciting PvP during the night and safely deactivating it during the day.

- Difficulty Adjustment: To add an extra challenge to your server, the plugin automatically adjusts the game difficulty
  when day or night begins.

- Support for Multiple Worlds: DayNightPvP works seamlessly across different worlds, allowing specific settings for each
  one.

- Customizable Configuration: Personalize the sounds that play at the start and end of PvP, and set custom messages to
  notify players.

- PlaceholderAPI Support: Easily display whether PvP is enabled or disabled on the player's Scoreboard or TAB.

- GriefPrevention Compatibility: You can choose whether GriefPrevention-protected lands will be safe during the night or
  also subject to PvP.

- Vault Support: With Vault support, you can determine if players should lose money upon dying during PvP and if the
  killer will receive the reward.

- WorldGuard Support: Easily define whether a WorldGuard region should have PvP enabled even during the day.

- Custom Day/Night Duration: Adjust the length of day and night cycles to suit your preferences.

# Metrics

[<img src="https://bstats.org/signatures/bukkit/daynightpvp.svg">](https://bstats.org/plugin/bukkit/DayNightPvP/19067/)

# PlaceHolders

    %dnp_current_world_pvpstatus% → Status of PvP in the current world where the player is.
    %dnp_pvpstatus_<worldName>% → PvP status of the defined world.

# Commands

    /dnp → Show all available commands
    /dnp reload → Reload the plugin

# WorldGuard Flags

	allow-pvp-on-day → Allows players to engage in combat even when it's daytime in a WorldGuard region (the "pvp" flag must be set to allow for it to work).

# Permissions

    dnp.admin → /dnp command access.
    dnp.losemoney.<percentage> → Percentage of money the user should lose upon death.
	dnp.bypass → Allows the user to engage in combat even during the day.
    dnp.immune → Allows the user to become immune to PvP.
