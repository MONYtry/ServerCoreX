package de.main.serverCoreX.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class time implements CommandExecutor, TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            // Erstellt einen Array, welcher anhand der Nutzer-Eingabe zurück geben wird
            return Arrays.asList("morning", "noon", "afternoon", "evening","midnight").stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("settime"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        World world = p.getWorld();
        if (args.length == 0)
        {
            p.sendMessage("Zu wenig argumente! (Nutze /weather sun,storm)");
            return true;
        }


        switch (args[0].toLowerCase()) {
            case "morning":
                world.setTime(0L);
                sendPlayerMessage(p, "§eZeit §7wurde auf §eMorgens §7gesetzt");
                break;

            case "noon":
                world.setTime(6000L);
                sendPlayerMessage(p, "§eZeit §7wurde auf §eMittags §7gesetzt");
                break;

            case "afternoon":
                world.setTime(9000L);
                sendPlayerMessage(p, "§eZeit §7wurde auf §eNachmittags §7gesetzt");
                break;

            case "evening":
                world.setTime(12000L);
                sendPlayerMessage(p, "§eZeit §7wurde auf §eAbends §7gesetzt");
                break;

            case "midnight":
                world.setTime(18000L);
                sendPlayerMessage(p, "§eZeit §7wurde auf §eMitternacht §7gesetzt");
                break;
        }
        return true;
    }
}
