package de.main.serverCoreX;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import static de.main.serverCoreX.helper.rank.getRank;

public class prefix {


    public void applyPrefix(Player viewer) {

        Scoreboard scoreboard = viewer.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {
            // Holt sich den Prefix
            String prefix = getRank(target) +  "§7 |§f ";

            // Ertstellt / holt Team damit jeder sein eigenes hat
            String teamName = "prefix_" + target.getUniqueId().toString().replace("-", "").substring(0, 10);


            String imageToUse = null;
            String cleanRank = getRank(target).replaceAll("§.","");

            Bukkit.getLogger().info(cleanRank);
            switch (cleanRank.toLowerCase())
            {
                case "owner":
                    imageToUse = "\uE006";
                    break;
                default:
                    imageToUse = null;
                    break;
            }

            Component headerComponent = Component.empty();
            if (imageToUse != null)
            {
                Component logo = Component.text(imageToUse).font(Key.key("kasai", "default"));
                headerComponent = Component.empty().append(logo).append(Component.text(" §7| §f"));
            }
            else
                headerComponent = Component.empty().append(Component.text(prefix));

            Team team = scoreboard.getTeam(teamName);
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            // Fügt den Prefix hinzu
            team.prefix(headerComponent);
            team.addEntry(target.getName());
        }
    }

}