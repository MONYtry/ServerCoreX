package de.main.serverCoreX.prefix;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static de.main.serverCoreX.helper.rank.getDisplayRank;
import static de.main.serverCoreX.helper.rank.getRank;
import static de.main.serverCoreX.prefix.PrefixSettings.getCustomPrefix;

public class prefix {


    public void applyPrefix(Player viewer) {

        Scoreboard scoreboard = viewer.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {
            // Holt sich den Prefix
            String prefix = getRank(target) +  "§7 |§f ";

            // Ertstellt / holt Team damit jeder sein eigenes hat
            String teamName = "prefix_" + target.getUniqueId().toString().replace("-", "").substring(0, 10);
            Component headerComponent = getDisplayRank(target);

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