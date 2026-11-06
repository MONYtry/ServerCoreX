package de.main.serverCoreX.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player))
            return true;

        UUID uuid = player.getUniqueId();

        if (!TeleportManager.teleportRequests.containsKey(uuid)) {
            sendPlayerMessage(player, "§7Du hast keine offenen §eTeleportations-Anfragen!");
            return true;
        }

        Player partner = Bukkit.getPlayer(TeleportManager.teleportRequests.get(uuid));

        if (partner == null)
        {
            sendPlayerMessage(player, "§7Der Spieler ist nicht online!");
            TeleportManager.teleportRequests.remove(uuid);
            TeleportManager.teleportHere.remove(uuid);
            return true;
        }

        boolean here = TeleportManager.teleportHere.getOrDefault(uuid, false);

        if (here)
            player.teleport(partner);
        else
            partner.teleport(player);

        sendPlayerMessage(player, "§7Teleportations-Anfrage angenommen!");
        sendPlayerMessage(partner, "§7Deine Anfrage wurde angenommen!");

        TeleportManager.teleportRequests.remove(uuid);
        TeleportManager.teleportHere.remove(uuid);

        return true;
    }
}