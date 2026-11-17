package de.main.serverCoreX.helper;

import de.main.serverCoreX.economy.EconomyManager;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.main.serverCoreX.helper.customImages.getPrefix;
import static de.main.serverCoreX.prefix.PrefixSettings.getCustomPrefix;
import static de.main.serverCoreX.scoreboard.ScoreboardSettings.getCustomScoreboard;
import static de.main.serverCoreX.tablist.TablistSettings.getCustomtablist;

public class placeholder {
    private static ArrayList<String> placeholders = new ArrayList<>();
    static {
        placeholders.add("%get_balance%");
        placeholders.add("%get_prefix%");
        placeholders.add("%get_settings_prefix%");
        placeholders.add("%get_settings_scoreboard%");
        placeholders.add("%get_settings_tablist%");
    }


    public static void replacePlaceholder(ArrayList<String> list, Player p)
    {
        List<String> stringsToCheck = placeholders;

        for (int i = 0; i < list.size(); i++)
        {
            String line = list.get(i);
            for (String placeholder : stringsToCheck)
            {
                if (line.contains(placeholder))
                {
                    line = line.replace(placeholder, getPlaceholder(placeholder,p));
                }

            }
            list.set(i,line);
        }
    }

    private static String getPlaceholder(String toUse, Player p)
    {
        UUID uuid = p.getUniqueId();
        String activ = "§aaktiviert";
        String deactive = "§cdeaktiviert";

        switch (toUse)
        {
            case "%get_balance%":
                return EconomyManager.getOnlineBalance(p.getUniqueId()) + "$";

            case "%get_prefix%":
                return getPrefix(p);

            case "%get_settings_prefix%":
                return (getCustomPrefix(uuid)) ? activ : deactive;

            case "%get_settings_scoreboard%":
                return (getCustomScoreboard(uuid)) ? activ : deactive;

            case "%get_settings_tablist%":
                return (getCustomtablist(uuid)) ? activ : deactive;

            default:
                return "Error! Check Placeholder";

        }
    }
}
