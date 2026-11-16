package de.main.serverCoreX.helper;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import static de.main.serverCoreX.prefix.PrefixSettings.getCustomPrefix;


public class rank {

    public static String getRank(Player p)
    {
        return PlaceholderAPI.setPlaceholders(p,"%luckperms_prefix%");
    }


    public static Component getDisplayRank(Player p)
    {
        String prefix = getRank(p) + "§7| §f";
        Component headerComponent = Component.empty();

        if (getCustomPrefix(p.getUniqueId()))
        {
            String imageToUse = null;
            String cleanRank = getRank(p).replaceAll("§.","");

            switch (cleanRank.toLowerCase())
            {
                case "owner":
                    imageToUse = "\uE006";
                    break;
                default:
                    imageToUse = null;
                    break;
            }

            if (imageToUse == null)
                return headerComponent;

            Component logo = Component.text(imageToUse).font(Key.key("kasai", "default"));
            headerComponent = Component.empty().append(logo).append(Component.text(" §7| §f"));
        }

        else
            headerComponent = Component.empty().append(Component.text(prefix));
        return headerComponent;
    }
}
