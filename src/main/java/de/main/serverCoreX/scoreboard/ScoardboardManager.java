package de.main.serverCoreX.scoreboard;

import org.bukkit.Bukkit;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.key.Key;

import static de.main.serverCoreX.commands.vanish.vanishManager.getVanishedPlayers;
import static de.main.serverCoreX.helper.customImages.setTextureInScoreboard;
import static de.main.serverCoreX.helper.timer.repeat;

import static de.main.serverCoreX.scoreboard.ScoreboardSettings.*;
import static de.main.serverCoreX.tablist.tablist.updateTablist;

public class ScoardboardManager {

    public static void createScoreboard(Player p)
    {
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int maxOnlinePlayers = Bukkit.getMaxPlayers();
        String playerDisplay = "§f" + onlinePlayers  + "/" + maxOnlinePlayers;

        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("citybuild","dummy","§6§l BLOCKHIVE ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        addToCustomScoreboard(p.getUniqueId());
        objective.getScore("§3").setScore(9);

        objective.getScore("§8» §6§lServer").setScore(8);
        objective.getScore("§f"+p.getWorld().getName().toUpperCase()).setScore(7);

        objective.getScore("§2").setScore(6);

        objective.getScore("§8» §6§lSpieler").setScore(5);

        objective.getScore("§f" + playerDisplay).setScore(4);

        objective.getScore("§1").setScore(3);

        objective.getScore("§8» §6§lKonto").setScore(2);
        String balance = PlaceholderAPI.setPlaceholders(p, "%vault_eco_balance_commas%");

        objective.getScore("§f" + balance + "$").setScore(1);

        p.setScoreboard(board);
    }


    public static void update()
    {
        repeat(() -> {
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            onlinePlayers -= getVanishedPlayers().size();

            int maxOnlinePlayers = Bukkit.getMaxPlayers();
            updateTablist();
            for (Player p : Bukkit.getOnlinePlayers())
            {
                // Holt sich das Scoreboard des Spielers
                Scoreboard board = p.getScoreboard();

                // Holt sich die Objective
                Objective objective = board.getObjective("citybuild");

                if (objective == null)
                {
                    createScoreboard(p);
                    continue;
                }

                if (getCustomScoreboard(p.getUniqueId()))
                {
                    // Setzt den Titel als Custom-Texture
                    objective.displayName(Component.text("\uE001").font(Key.key("kasai", "default")));
                    // Setzt Custom-Texturen für das gesamte Scoreboard
                    setTextureInScoreboard(objective,board,"§5","server_icon","\uE002","kasai",8);
                    setTextureInScoreboard(objective,board,"§7","konto_icon","\uE004","kasai",2);
                    setTextureInScoreboard(objective,board,"§6","spieler_icon","\uE003","kasai",5);


                    objective.getScore("§9").setScore(9);
                    objective.getScore("§9").setScore(10);

                    board.resetScores("§8» §6§lServer");
                    board.resetScores("§8» §6§lKonto");
                    board.resetScores("§8» §6§lSpieler");
                }
                else
                {
                    objective.displayName(Component.text("§6§lBLOCKHIVE"));
                    objective.getScore("§8» §6§lServer").setScore(8);
                    objective.getScore("§8» §6§lSpieler").setScore(5);
                    objective.getScore("§8» §6§lKonto").setScore(2);

                    for (int i = 5; i < 9; i++)
                    {
                        board.resetScores("§" + i);
                    }
                }

                // Updaten des Scores
                String playerDisplay = "§f" + onlinePlayers  + "/" + maxOnlinePlayers;
                board.resetScores("§f" + playerDisplay);

                // Setzt das Konto über PlaceholderAPI
                String balance = PlaceholderAPI.setPlaceholders(p, "%vault_eco_balance_commas%");

                board.resetScores("§f" + balance);

                // Geht durch alle Einträge des Scoreboards
                for (String entry : board.getEntries())
                {
                    // Falls es Weis ist und Geld beinhaltet, reset
                    if (entry.startsWith("§f") && entry.contains("$"))
                    {
                        board.resetScores(entry);
                    }
                    if (entry.startsWith("§f") && entry.contains("/"))
                    {
                        board.resetScores(entry);
                    }
                }
                objective.getScore("§f" + balance + "$").setScore(1);
                objective.getScore(playerDisplay).setScore(4);

            }
        }, 20L * 10);
    }
}
