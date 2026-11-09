package de.main.serverCoreX.commands.speed;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.main.serverCoreX.helper.messages.getNoPerm;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class flySpeedCommand implements CommandExecutor, TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            // Erstellt einen Array, welcher anhand der Nutzer-Eingabe zurück geben wird
            return Arrays.asList("normal", "1", "5", "10").stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("flyspeed"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        String perm = "size.use";
        if (!p.hasPermission(perm))
        {
            sendPlayerMessage(p,getNoPerm());
            return true;
        }

        if (args.length != 1) {
            sendPlayerMessage(p,"§7Hey, deine §eEingabe §7ist §eungültig! §7(Nutze /size 1.5)");
            return true;
        }

        try {
            double speed = Double.parseDouble(args[0]);

            speed = Math.clamp(speed, 1.0, 10.0) / 10.0;

            p.setFlySpeed((float) speed);

            sendPlayerMessage(p, "§7Deine §eFlug-Geschwindigkeit §7wurde auf §e" + (speed * 10) + " §7gesetzt!");
        }
        catch (NumberFormatException e)
        {
            switch (args[0].toLowerCase())
            {
                case "normal":
                    p.setFlySpeed(0.2f);
                    sendPlayerMessage(p, "§7Deine §eFlug-Geschwindigkeit §7wurde auf §e" + (0.2 * 10) + " §7gesetzt!");
                    break;

                default:
                    sendPlayerMessage(p, "§cUngültige Eingabe!");
                    break;
            }

        }
        return true;
    }
}
