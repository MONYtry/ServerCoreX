package de.main.serverCoreX.commands.broadcast;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class broadcast implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("broadcast"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (!hasPermission(p,"broadcast.use"))
            return true;

        if (args.length < 1)
        {
            sendPlayerMessage(p, "§7Deine Eingabe war falsch benutze (/broadcast <Message>");
            return true;
        }
        handleMessage(String.join(" ", args),p);

        sendPlayerMessage(p, "§eBroadcast §7wurde erfolgreich §eausgeführt");
        return true;
    }

    private void handleMessage(String text, Player p) {
        NamedTextColor currentColor = NamedTextColor.GRAY;
        String[] splitMessage = text.split("&");
        Component message = Component.empty();

        boolean obfuscated = false;
        boolean bold = false;
        boolean striketrough = false;
        boolean italic = false;
        boolean underline = false;

        for (String s : splitMessage) {
            if (s == null || s.isEmpty())
                continue;

            char code = s.charAt(0);
            String content = s.substring(1);

            switch (code) {
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
                    .decoration(TextDecoration.BOLD, bold)
                    .decoration(TextDecoration.STRIKETHROUGH, striketrough)
                    .decoration(TextDecoration.UNDERLINED, underline)
                    .decoration(TextDecoration.ITALIC, italic);
            message = message.append(part);
        }

        for (Player target : Bukkit.getOnlinePlayers())
        {
            target.showTitle(Title.title(message,Component.text("§fWichtige Ankündigung von §b@" + p.getName())));
        }
    }
}
