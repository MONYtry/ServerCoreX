package de.main.serverCoreX;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class prefix {


    public void applyPrefix(Player viewer) {

        Scoreboard scoreboard = viewer.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {
            // Holt sich den Prefix
            String prefix = PlaceholderAPI.setPlaceholders(target,"%luckperms_prefix%") + "§7 |§f ";

            // Ertstellt / holt Team damit jeder sein eigenes hat
            String teamName = "prefix_" + target.getUniqueId().toString().replace("-", "").substring(0, 10);
            Team team = scoreboard.getTeam(teamName);

            if (team == null) {
                team = scoreboard.registerNewTeam(teamName);
            }
            // Fügt den Prefix hinzu
            team.prefix(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix));
            team.addEntry(target.getName());
        }
    }

}