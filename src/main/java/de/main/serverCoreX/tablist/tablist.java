
package de.main.serverCoreX.tablist;

import de.main.serverCoreX.ServerCoreX;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static de.main.serverCoreX.commands.vanish.vanishManager.getVanishedPlayer;
import static de.main.serverCoreX.commands.vanish.vanishManager.getVanishedPlayers;

public class tablist {

    public static void updateTablist() {

        FileConfiguration file = ServerCoreX.getInstance().tablist;

        if (file == null) {
            Bukkit.getLogger().warning("Es ist ein Fehler beim Laden der Tablist-File aufgetreten! z.16");
            return;
        }

        LuckPerms luckPerms = LuckPermsProvider.get();
        List<Player> players = new ArrayList<>();

        int getOnlinePlayers = Bukkit.getOnlinePlayers().size();
        getOnlinePlayers -= getVanishedPlayers().size();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!getVanishedPlayer(p.getUniqueId())) {
                players.add(p);
            }
        }

        // Spieler nach LuckPerms-Gruppen sortieren
        players.sort(
                Comparator.comparingInt((Player p) -> {
                    User user = luckPerms.getPlayerAdapter(Player.class).getUser(p);

                    return switch (user.getPrimaryGroup().toLowerCase()) {
                        case "owner" -> 2;
                        case "admin" -> 1;
                        default -> 0;
                    };
                }).thenComparing(Player::getName, String.CASE_INSENSITIVE_ORDER)
        );

        // Tablist-Reihenfolge setzen
        for (int i = 0; i < players.size(); i++) {

            Player p = players.get(i);

            p.setPlayerListOrder(i);

            // Erstellung der Variablen mit Config und PlaceholderAPI
            String header = PlaceholderAPI.setPlaceholders(
                    p, file.getString("header", "§6§lBLOCKHIVE.NET")
            );

            String footer = PlaceholderAPI.setPlaceholders(
                    p, file.getString("footer", "§7Online: §e%online% §8| §7Website: §eBLOCKHIVE.INFO")
            );

            footer = footer.replace("%online%", String.valueOf(getOnlinePlayers));

            String tabName = PlaceholderAPI.setPlaceholders(
                    p, file.getString("tab-name", "%luckperms_prefix% §7| §f%player_name%")
            );

            // Setzt die Tablist
            p.setPlayerListName(tabName);

            // Sendet Header und Footer
            p.sendPlayerListHeaderAndFooter(
                    Component.text(header),
                    Component.text(footer)
            );
        }
    }
}
