package de.main.serverCoreX.commands.teleport;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.*;

public class tphereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("tphere"))
            return true;

        if (!(sender instanceof Player p))
            return true;



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

            // Falls Spieler Bypass hat
            if (p.isOp())
            {
                partner.teleport(p);
                return true;
            }

            TeleportManager.teleportRequests.put(partner.getUniqueId(), p.getUniqueId());
            TeleportManager.teleportHere.put(partner.getUniqueId(), true);

            // Nachricht an den Nutzer
            sendPlayerMessage(p, "Teleportations-Anfrage an "+ ChatColor.YELLOW + partner.getName() + "§7 gesendet!");

            // Nachricht an den Partner
            TextComponent message = new TextComponent(getPrefix() + "§7Klicke hier um die Anfrage von §e" + p.getName() + " §7anzunehmen " + ChatColor.YELLOW + " [AKTZEPTIEREN]");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpyes"));
            partner.spigot().sendMessage(message);
            sendPlayerMessage(partner,p.getName() + "§7 möchte sich zu dir §eteleportieren!");
        }
        return true;
    }
}
