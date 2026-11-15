package de.main.serverCoreX.helper;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;


public class rank {

    public static String getRank(Player p)
    {
        return PlaceholderAPI.setPlaceholders(p,"%luckperms_prefix%");
    }
}
