package de.main.serverCoreX.prefix;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static de.main.serverCoreX.helper.customImages.getDisplayRank;

public class prefix {


    public void applyPrefix(Player viewer) {

        Scoreboard scoreboard = viewer.getScoreboard();

        for (Player target : Bukkit.getOnlinePlayers()) {

            // Ertstellt / holt Team damit jeder sein eigenes hat
            String teamName = "prefix_" + target.getUniqueId().toString().replace("-", "").substring(0, 10);
            Component headerComponent = getDisplayRank(target,false);

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