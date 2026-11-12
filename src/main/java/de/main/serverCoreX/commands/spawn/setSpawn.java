package de.main.serverCoreX.commands.spawn;


import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.commands.spawn.locationManager.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;


public class setSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("setspawn"))
            return true;

        if (!(sender instanceof Player p))
            return true;
        if (!hasPermission(p,"spawn.setspawn"))
            return true;
        Location spawnLocation = getLocation("spawn");

        if (spawnLocation == null)
        {
            spawnLocation = p.getLocation();
            sendPlayerMessage(p,"§eSpawn §7wurde erfolgreich §egesetzt!");
            saveLocation("spawn",spawnLocation);
            return true;
        }
        sendPlayerMessage(p,"§cEs existiert bereits ein Spawn!");
        return true;
    }
}
