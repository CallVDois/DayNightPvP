package org.callvdois.daynightpvp.placeholder;

import org.callvdois.daynightpvp.DayNightPvP;
import org.callvdois.daynightpvp.config.ConfigManager;

public class RegisterPlaceHolder {

    public void register() {
        if (DayNightPvP.placeHolderIsPresent) {
            if (ConfigManager.placeholderPlaceholders) {
                PvpStatus pvpStatus = new PvpStatus();
                pvpStatus.unregister();
                pvpStatus.register();
            }
        }
    }

}
