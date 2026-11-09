package de.main.serverCoreX.tablist;

import de.main.serverCoreX.ServerCoreX;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static de.main.serverCoreX.commands.vanish.vanishManager.getVanishedPlayers;

public class tablist {

    public static void updateTablist()
    {
        FileConfiguration file = ServerCoreX.getInstance().tablist;
        if (file == null)
        {
            Bukkit.getLogger().warning("Es ist ein Fehler beim Laden der Tablist-File aufgetreten! z.16");
        }

        int getOnlinePlayers = Bukkit.getOnlinePlayers().size();
        getOnlinePlayers -= getVanishedPlayers().size();

        for (Player p : Bukkit.getOnlinePlayers())
        {

            // Erstellung der Variablen mit Config und PlaceholderAPI
            String header = PlaceholderAPI.setPlaceholders(p,file.getString("header","§6§lBLOCKHIVE.NET"));

            String footer = PlaceholderAPI.setPlaceholders(p,file.getString("footer", "§7Online: §e%online% §8| §7Website: §eBLOCKHIVE.INFO"));
            footer = footer.replace("%online%", String.valueOf(getOnlinePlayers));

            String tabName = PlaceholderAPI.setPlaceholders(p, file.getString("tab-name","%luckperms_prefix% §7| §f%player_name%"));

            // Setzt die Tablist
            p.setPlayerListName(tabName);
            // Sendet die Tablist mit Header und Footer
            p.sendPlayerListHeaderAndFooter(Component.text(header),Component.text(footer));
        }
    }
}
