package de.main.serverCoreX.commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.*;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!command.getName().equalsIgnoreCase("fly"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (!hasPermission(p,"fly.use"))
            return true;

        if (p.getAllowFlight())
        {
            p.setAllowFlight(false);
            p.setFlying(false);
            sendPlayerMessage(p,getFlightDisabled());
            return true;
        }

        p.setAllowFlight(true);
        p.setFlying(true);
        sendPlayerMessage(p,getFlightEnabled());

        return true;
    }
}
