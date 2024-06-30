package org.callvdois.daynightpvp.service;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.LangManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {

    private final LangManager langManager;

    public UpdateChecker() {
        langManager = new LangManager();
    }

    public void checkUpdate(PlayerJoinEvent event) {
        try {
            long random = (long) Math.floor(Math.random() * (Long.MAX_VALUE - 1 + 1) + 1);
            String currentVersion = DayNightPvP.getInstance().getDescription().getVersion();
            String latestVersion = verifyPluginVersion("https://api.spigotmc.org/legacy/update.php?resource=102250&t=" + random);

            if (!currentVersion.equals(latestVersion)) {

                TextComponent link = new TextComponent(langManager.getActionUpdateFoundClick());
                link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/daynightpvp-dynamic-pvp-for-day-night.102250/updates"));

                event.getPlayer().sendMessage(langManager.getFeedbackUpdateFound());
                event.getPlayer().sendMessage(langManager.getFeedbackUpdateFoundCurrentVersion().replace("{0}", currentVersion));
                event.getPlayer().sendMessage(langManager.getFeedbackUpdateFoundNewVersion().replace("{0}", latestVersion));
                event.getPlayer().spigot().sendMessage(link);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String verifyPluginVersion(String url) throws IOException {

        URL urlForGetRequest = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String readLine;
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            return response.toString();
        } else {
            return "GET NOT WORKED";
        }
    }

}
