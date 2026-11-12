package de.main.serverCoreX.commands.spawn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.commands.spawn.locationManager.getLocation;
import static de.main.serverCoreX.commands.spawn.locationManager.saveLocation;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class delSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("delspawn"))
            return true;

        if (!(sender instanceof Player p))
            return true;
        if (!hasPermission(p,"spawn.delspawn"))
            return true;

        Location spawnLocation = getLocation("spawn");

        if (spawnLocation == null)
        {
            sendPlayerMessage(p,"§7Es §eexistiert §7bereits kein §eSpawn!");
            return true;
        }

        saveLocation("spawn",null);
        sendPlayerMessage(p,"§7Der §eSpawn §7wurde §egelöscht!");
        return true;
    }
}
