package de.main.serverCoreX.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class SendCommand implements CommandExecutor {

    public static HashMap<UUID,UUID> teleportPartners = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("tpa"))
            return true;

        if (!(sender instanceof Player player))
            return true;

        if (args.length == 0)
        {
            player.sendMessage("Bitte gebe einen Teleportations-Partner an!");
            return true;
        }
        Player partner = null;

        if (args.length >= 1)
        {
            partner = Bukkit.getPlayer(args[0]);
            if (partner == null)
                return true;
            teleportPartners.put(partner.getUniqueId(), player.getUniqueId());
            partner.sendMessage(partner.getName() + " will sich zu dir Teleportieren (nutze /tpyes)");
        }
        return true;
    }
}
