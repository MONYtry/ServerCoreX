package de.main.serverCoreX.helper;

import de.main.serverCoreX.economy.EconomyManager;
import org.bukkit.entity.Player;
import java.util.ArrayList;

import static de.main.serverCoreX.helper.customImages.getPrefix;

public class placeholder {
    private static ArrayList<String> getArray()
    {
        ArrayList<String> stringsToCheck = new ArrayList<>();
        stringsToCheck.add("%get_balance%");
        stringsToCheck.add("%get_prefix%");

        return stringsToCheck;
    }

    public static void replacePlaceholder(ArrayList<String> list, Player p)
    {
        ArrayList<String> stringsToCheck = getArray();

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
        switch (toUse)
        {
            case "%get_balance%":
                return EconomyManager.getOnlineBalance(p.getUniqueId()) + "$";

            case "%get_prefix%":
                return getPrefix(p);

            default:
                return "Error! Check Placeholder";

        }
    }
}
