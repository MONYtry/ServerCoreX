package de.main.serverCoreX.commands.spawn;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.commands.spawn.locationManager.getLocation;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class spawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("spawn"))
            return true;

        if (!(sender instanceof Player p))
            return true;
        Location spawnLocation = getLocation("spawn");

        if (spawnLocation == null)
        {
            sendPlayerMessage(p,"§7Es konnte kein §eSpawn §7gefunden werden!");
            return true;
        }
        p.teleport(spawnLocation);
        sendPlayerMessage(p,"§7Du wurdest §eteleportiert!");
        return true;
    }
}
