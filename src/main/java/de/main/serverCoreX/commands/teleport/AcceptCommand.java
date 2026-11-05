package de.main.serverCoreX.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.commands.teleport.SendCommand.teleportPartners;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("tpyes"))
            return true;

        if (!(sender instanceof Player player))
            return true;

        if (!teleportPartners.containsKey(player.getUniqueId()))
        {
            player.sendMessage("Du hast keine offenen Teleportations-Anfragen!");
            return true;
        }
        Player partner = Bukkit.getPlayer(teleportPartners.get(player.getUniqueId()));
        if (partner == null)
        {
            player.sendMessage("Der Spieler ist nicht Online oder konnte nicht gefunden werden");
            return true;
        }
        player.teleport(partner);
        teleportPartners.remove(player.getUniqueId());
        return true;
    }
}
