package de.main.serverCoreX.listener.chat;

import de.main.serverCoreX.ServerCoreX;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


import java.util.Set;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.timer.delay;
import static de.main.serverCoreX.listener.chat.messageManager.chatCooldown;

public class messageListener implements Listener {
    @EventHandler
    public void onMessage(AsyncChatEvent e)
    {
        Player p = e.getPlayer();

        if (chatCooldown.contains(p.getUniqueId()) && !p.hasPermission("chat.cooldown.bypass"))
        {
            e.setCancelled(true);

            sendPlayerMessage(p,"§7Hey du §7sendest §7zu schnell §eNachrichten!");
            return;
        }

        if (!p.hasPermission("chat.cooldown.bypass"))
        {
            chatCooldown.add(p.getUniqueId());
            delay(() -> chatCooldown.remove(p.getUniqueId()),20L * 3);
        }

        String rawMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

        String prefix = PlaceholderAPI.setPlaceholders(p,"%luckperms_prefix%");


        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getPlayerAdapter(Player.class).getUser(p);

        String rang = user.getPrimaryGroup().toLowerCase();


        if (!rawMessage.contains("&"))
        {
            Component finalMessage = Component.empty()
                    .append(Component.text(prefix + " §8| "))
                    .append(p.displayName().color(NamedTextColor.GRAY))
                    .append(Component.text(" §8» "))
                    .append(e.message().color(NamedTextColor.GRAY));

            sendChatMessage(e,finalMessage,rang);
            return;
        }

        Component message = Component.empty();
        String[] splitMessage = rawMessage.split("&");
        NamedTextColor currentColor = NamedTextColor.GRAY;

        boolean obfuscated = false;
        boolean bold = false;
        boolean striketrough = false;
        boolean italic = false;
        boolean underline = false;

        for (String s : splitMessage)
        {
            if (s == null || s.isEmpty())
                continue;

            char code = s.charAt(0);
            String content = s.substring(1);

            switch (code)
            {
                // SCHWARZ WEIS
                case 'f':
                    currentColor = NamedTextColor.WHITE;
                    break;
                case '0':
                    currentColor = NamedTextColor.BLACK;
                    break;

                // GRAU
                case '8':
                    currentColor = NamedTextColor.DARK_GRAY;
                    break;
                case '7':
                    currentColor = NamedTextColor.GRAY;
                    break;

                // LILA
                case '5':
                    currentColor = NamedTextColor.DARK_PURPLE;
                    break;
                case 'd':
                    currentColor = NamedTextColor.LIGHT_PURPLE;
                    break;

                // BLAU
                case '9':
                    currentColor = NamedTextColor.BLUE;
                    break;
                case '1':
                    currentColor = NamedTextColor.DARK_BLUE;
                    break;

                // AQUA
                case 'b':
                    currentColor = NamedTextColor.AQUA;
                    break;
                case '3':
                    currentColor = NamedTextColor.DARK_AQUA;
                    break;

                // GELB
                case 'e':
                    currentColor = NamedTextColor.YELLOW;
                    break;
                case '6':
                    currentColor = NamedTextColor.GOLD;
                    break;

                // GRÜN
                case 'a':
                    currentColor = NamedTextColor.GREEN;
                    break;

                case '2':
                    currentColor = NamedTextColor.DARK_GREEN;
                    break;

                // ROT
                case '4':
                    currentColor = NamedTextColor.DARK_RED;
                    break;

                case 'c':
                    currentColor = NamedTextColor.RED;
                    break;

                // SONSTIGE
                case 'l':
                    bold = true;
                    break;

                case 'k':
                    obfuscated = true;
                    break;
                case 'n':
                    underline = true;
                    break;
                case 'o':
                    italic = true;
                    break;
                case 'm':
                    striketrough = true;
                    break;

                case 'r':
                    currentColor = NamedTextColor.GRAY;
                    obfuscated = false;
                    bold = false;
                    break;

                default:
                    content = s;
                    break;
            }

            Component part = Component.text(content)
                    .color(currentColor)
                    .decoration(TextDecoration.OBFUSCATED, obfuscated)
                    .decoration(TextDecoration.BOLD,bold)
                    .decoration(TextDecoration.STRIKETHROUGH,striketrough)
                    .decoration(TextDecoration.UNDERLINED,underline)
                    .decoration(TextDecoration.ITALIC, italic);
            message = message.append(part);
        }


        Component formattedMessage = Component.empty()
                .append(Component.text(prefix + " §8| "))
                .append(p.displayName().color(NamedTextColor.GRAY))
                .append(Component.text(" §8» "))
                .append(message);

        sendChatMessage(e, formattedMessage,rang);
    }


    private void sendChatMessage(AsyncChatEvent e, Component message,String rang) {
        boolean isImportant = false;
        e.setCancelled(true);
        Set<Audience> viewers = Set.copyOf(e.viewers());

        switch (rang.toLowerCase())
        {
            case "owner", "admin":
                isImportant = true;
                break;
            default:
                break;
        }

        boolean finalIsImportant = isImportant;
        Bukkit.getScheduler().runTask(ServerCoreX.getInstance(), () -> {
            if (!finalIsImportant)
            {
                for (Audience viewer : viewers)
                {
                    viewer.sendMessage(message);
                }
                return;
            }

            for (Audience viewer : viewers)
            {
                viewer.sendMessage(Component.text("§8»"));
                viewer.sendMessage(message);
                viewer.sendMessage(Component.text("§8»"));
            }
        });
    }
}
