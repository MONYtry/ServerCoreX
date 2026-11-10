package de.main.serverCoreX.listener;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import it.unimi.dsi.fastutil.chars.CharCollections;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.w3c.dom.Text;

public class messageListener implements Listener {
    @EventHandler
    public void onMessage(AsyncChatEvent e)
    {
        Player p = e.getPlayer();
        String rawMessage = PlainTextComponentSerializer.plainText().serialize(e.message());

        if (!rawMessage.contains("&"))
        {
            return;
        }


        Component message = Component.empty();
        String[] splitMessage = rawMessage.split("&");

        for (String s : splitMessage)
        {
            if (s == null || s.isEmpty())
                continue;

            switch (s.charAt(0))
            {
                case 'e':
                    s = s.substring(1);
                    message = message.append(Component.text(s, NamedTextColor.YELLOW));
                    break;

                case 'a':
                    s = s.substring(1);
                    message = message.append(Component.text(s, NamedTextColor.GREEN));
                    break;

                case 'k':
                    s = s.substring(1);
                    message = message.append(Component.text(s).decorate(TextDecoration.OBFUSCATED));
                    break;

                case 'r':
                    s = s.substring(1);
                    message = message.append(Component.text(s)
                            .decoration(TextDecoration.BOLD, false)
                            .decoration(TextDecoration.ITALIC, false)
                            .decoration(TextDecoration.UNDERLINED, false)
                            .decoration(TextDecoration.STRIKETHROUGH, false)
                            .decoration(TextDecoration.OBFUSCATED, false));
                    break;

                case 'b':
                    s = s.substring(1);
                    message = message.append(Component.text(s, NamedTextColor.BLUE));
                    break;

                case '9':
                    s = s.substring(1);
                    message = message.append(Component.text(s, NamedTextColor.DARK_BLUE));
                    break;

                default:
                    s = s.substring(1);
                    message = message.append(Component.text(s,NamedTextColor.GRAY));
                    break;
            }

        }

        Component finalMessage = message;
        ChatRenderer chatRenderer = ((source, sourceDisplayName, originalMessage, viewer) ->
                Component.empty()
                                .append(sourceDisplayName)
                                .append(Component.text(": ")
                                .append(finalMessage)));

        e.renderer(chatRenderer);
    }
}
