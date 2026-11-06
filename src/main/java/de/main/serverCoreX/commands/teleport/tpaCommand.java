package de.main.serverCoreX.commands.teleport;

import de.main.serverCoreX.helper.timer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static de.main.serverCoreX.commands.teleport.TeleportManager.teleportHere;
import static de.main.serverCoreX.commands.teleport.TeleportManager.teleportRequests;
import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.timer.delay;

public class tpaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("tpa"))
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

            teleportRequests.put(partner.getUniqueId(), p.getUniqueId());
            teleportHere.put(partner.getUniqueId(),false);

            UUID uuid = partner.getUniqueId();
            Player displayPartner = partner;

            // Timer fürs ablaufen der Anfrage
            delay(() -> {
                if (teleportRequests.containsKey(uuid) && teleportHere.containsKey(uuid))
                {
                    TeleportManager.teleportRequests.remove(uuid);
                    TeleportManager.teleportHere.remove(uuid);

                    if (p.isOnline())
                    {
                        sendPlayerMessage(p,"Teleportations-Anfrage an " + displayPartner.getName() + " abgelaufen");
                    }
                    if (displayPartner.isOnline())
                    {
                        sendPlayerMessage(displayPartner,"Teleportations-Anfrage von " + p.getName() + " abgelaufen");
                    }
                }
            }, 20L * 60);

            // Anzeige Nachricht
            TextComponent message = new TextComponent(getPrefix() + "§7Klicke hier um die Anfrage von §e" + p.getName() + " §7anzunehmen " + ChatColor.YELLOW + " [AKTZEPTIEREN]");

            // Click-Event
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpyes"));

            // Hover-Message
            //message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke zum Annehmen!").create()));

            sendPlayerMessage(partner,p.getName() + "§7 möchte sich zu dir §eteleportieren!");

            // Sendet Nachricht über Spigot
            partner.spigot().sendMessage(message);

            sendPlayerMessage(p,"§eTeleportations-Anfrage §7an §e" + partner.getName() + " §7gesendet!");
            sendPlayerMessage(p,"§7Die Teleportations-Anfrage läuft in §e60 Sekunden §7ab!");
            sendPlayerMessage(partner,"§7Die Teleportations-Anfrage läuft in §e60 Sekunden §7ab!");
        }
        return true;
    }
}
