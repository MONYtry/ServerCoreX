package de.main.serverCoreX.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.*;

public class tpCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("tp"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (!p.isOp())
        {
            sendPlayerMessage(p,getNoPerm());
            return true;
        }

        if (args.length == 0)
        {
            sendPlayerMessage(p,"§7Bitte gebe einen §eTeleportations-Partner an!");
            return true;
        }

        if (args.length >= 1)
        {
            Player partner = null;
            partner = Bukkit.getPlayer(args[0]);
            if (partner == null)
            {
                sendPlayerMessage(p,"§7Der Spieler konnte nicht gefunden werden!");
                return true;
            }

            if (partner.getName().equalsIgnoreCase(p.getName()))
            {
                sendPlayerMessage(p,"§7Du kannst dich nicht selbst §7teleportieren!");
                return true;
            }

            p.teleport(partner);
            sendPlayerMessage(p,"§7Du wurdest §eteleportiert");
        }
        return true;
    }
}
