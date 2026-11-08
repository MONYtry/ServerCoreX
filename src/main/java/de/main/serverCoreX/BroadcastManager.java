package de.main.serverCoreX;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.timer.repeat;

public class BroadcastManager {
    static int indexNumber = 0;

    private static void createMessage(String text, boolean isClickable, String urlToOpen)
    {
        TextComponent empty = new TextComponent(getPrefix() + "");
        TextComponent line = new TextComponent(getPrefix() + "§8§l━━━━━━━━━━━━━━━━━━━━━━━━");

        Bukkit.broadcast(line);
        Bukkit.broadcast(empty);

        if (isClickable)
        {
            // Anzeige Nachricht
            TextComponent message = new TextComponent(getPrefix() + text);

            // Click-Event
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlToOpen));

            // Hover-Message
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke zum Annehmen!").create()));

            // Sendet Nachricht über Spigot
            Bukkit.broadcast(message);
        }
        else
            // Sendet Nachricht über Spigot
            Bukkit.broadcastMessage(getPrefix() + text);

        Bukkit.broadcast(empty);
        Bukkit.broadcast(line);
    }

    public static void startMessages()
    {
    repeat(() -> {
                switch (indexNumber)
                {
                    case 0 -> createMessage("§7Klicke §bhier§7 um auf §bDiscord §7zu gelangen!",true,"https://github.com/MONYtry");
                    case 1 -> createMessage("§7Nutze §e/belohnung §7um deine §eBelohungen §7abzuholen!",false,null);
                    case 2 -> createMessage("§7Nutze §e/help §7für Infos!",false,null);
                    default -> createMessage("§cUnerwarteter Fehler! (Kontaktiere einen Administrator!)", false,null);
                }

                indexNumber++;

                if (indexNumber == 3)
                    indexNumber = 0;

            },20L * 60 * 5
            );
    }
}
