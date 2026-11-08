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

public class weather implements CommandExecutor, TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            // Erstellt einen Array, welcher anhand der Nutzer-Eingabe zurück geben wird
            return Arrays.asList("sun", "rain", "storm").stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }

        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("setweather"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        World world = p.getWorld();
        if (args.length == 0)
        {
            sendPlayerMessage(p,"§7Zu wenig §eargumente! §7(Nutze /weather sun,storm,rain)");
            return true;
        }


        switch (args[0].toLowerCase())
        {
            case "sun":
                world.setStorm(false);
                world.setThundering(false);
                sendPlayerMessage(p,"§eWetter §7auf §eSonne §7gesetzt");
                break;

            case "rain":
                world.setStorm(true);
                world.setThundering(false);
                sendPlayerMessage(p,"§eWetter §7auf §eRegen §7gesetzt");
                break;

            case "storm":
                world.setStorm(true);
                world.setThundering(true);
                sendPlayerMessage(p,"§eWetter §7auf §eSturm §7gesetzt");

            default:
                sendPlayerMessage(p, "§cArgument §7wurde nicht §cgefunden §7(" + args[0] + ")");
                break;
        }
        return true;
    }
}
