package de.main.serverCoreX.commands.vanish;

import de.main.serverCoreX.ServerCoreX;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import static de.main.serverCoreX.commands.vanish.vanishManager.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class vanish implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("vanish"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        UUID uuid = p.getUniqueId();

        // Falls bereits im Vanish ist
        if (getVanishedPlayer(uuid))
        {
            // Entfernt Vanished-Spieler aus dem RAM
            removeFromVanishedPlayers(uuid);
            handleSuffix(p);
            sendPlayerMessage(p,"§7Du hast den §eVanish §7verlassen!");

            // Geht durch alle Spieler die gerade Online sind
            for (Player target : Bukkit.getOnlinePlayers())
            {
                // Macht den Spieler wieder sichtbar
                target.showPlayer(ServerCoreX.getProvidingPlugin(getClass()),p);
            }
            return true;
        }
        else
        {

            addToVanishedPlayers(uuid);
            handleSuffix(p);
            sendPlayerMessage(p,"§7Du hast den §eVanish §7betreten!");

            for (Player target : Bukkit.getOnlinePlayers())
            {
                if (!target.hasPermission("vanish.see"))
                {
                    target.hidePlayer(ServerCoreX.getProvidingPlugin(getClass()),p);
                }
            }

        }

        return true;
    }

    public static void handleVanishOnJoin(Player p)
    {
        for (UUID uuid : getVanishedPlayers())
        {
            Player vanishPlayer = Bukkit.getPlayer(uuid);
            if (vanishPlayer != null && !vanishPlayer.hasPermission("vanish.see"))
                p.hidePlayer(ServerCoreX.getProvidingPlugin(vanish.class),vanishPlayer);
        }
    }

    public static void handleSuffix(Player vanishedPlayer) {

        for (Player viewer : Bukkit.getOnlinePlayers()) {

            Scoreboard board = viewer.getScoreboard();

            Team team = board.getTeam("vanish");

            if (team == null) {
                team = board.registerNewTeam("vanish");
            }

            if (getVanishedPlayer(vanishedPlayer.getUniqueId())) {
                team.suffix(Component.text(" [V]", NamedTextColor.RED));
                team.addEntry(vanishedPlayer.getName());
            } else {
                team.removeEntry(vanishedPlayer.getName());
            }
        }
    }
}
