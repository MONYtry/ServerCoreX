package de.main.serverCoreX.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;


public class discord implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("discord"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        // Anzeige Nachricht
        TextComponent message = new TextComponent(getPrefix() +" §7Klicke §bhier§7 um zu §bDiscord §7zu gelangen!");

        // Click-Event
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/MONYtry"));

        // Hover-Message
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke zum Annehmen!").create()));

        sendPlayerMessage(p,"§8§l━━━━━━━━━━━━━━━━━━━━━━━━");
        sendPlayerMessage(p,"");

        // Sendet Nachricht über Spigot
        p.spigot().sendMessage(message);

        sendPlayerMessage(p,"");
        sendPlayerMessage(p,"§8§l━━━━━━━━━━━━━━━━━━━━━━━━");

        return true;
    }

    public static void sendGlobalDiscordMessage()
    {
        TextComponent empty = new TextComponent(getPrefix() + "");
        TextComponent line = new TextComponent(getPrefix() + "§8§l━━━━━━━━━━━━━━━━━━━━━━━━");

        // Anzeige Nachricht
        TextComponent message = new TextComponent(getPrefix() +" §7Klicke §bhier§7 um zu §bDiscord §7zu gelangen!");

        // Click-Event
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/MONYtry"));

        // Hover-Message
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke zum Annehmen!").create()));

        Bukkit.broadcast(line);
        Bukkit.broadcast(empty);

        // Sendet Nachricht über Spigot
        Bukkit.broadcast(message);

        Bukkit.broadcast(empty);
        Bukkit.broadcast(line);
    }
}
