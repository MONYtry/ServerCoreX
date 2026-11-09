package de.main.serverCoreX.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class ecoCommand implements CommandExecutor, TabCompleter {
    private final EconomyManager manager;

    public ecoCommand(EconomyManager manager) {
        this.manager = manager;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        if (args.length == 1)
        {
            return Arrays.asList("give", "set", "take").stream().filter(s -> s.startsWith(args[0].toLowerCase())).toList();
        }

        if (args.length == 2)
        {
            return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("eco"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (args.length < 3)
        {
            sendPlayerMessage(p,"§7Zu wenige §eArgumente §7nutze §e/eco give, set, take");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null)
        {
            sendPlayerMessage(p,"§7Dieser §eSpieler §7konnte nicht §egefunden §7werden");
            return true;
        }
        UUID uuid = target.getUniqueId();

        int amount = 0;
        try
        {
            amount = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        switch (args[0].toLowerCase())
        {
            case "set":
                manager.setBalance(uuid, amount);
                sendPlayerMessage(p, "§7Das §eKonto §7des §7Spielers §e" + target.getName() + " §7wurde auf §e" + amount + " §7gesetzt");
            break;

            case "give":
                manager.addBalance(uuid, amount);
                sendPlayerMessage(p, "§7Das §eKonto §7des §7Spielers §e" + target.getName() + " §7wurde um §e" + amount + " §7erhöht");
            break;

            case "take":
                manager.removeFromBalance(uuid, amount);
                sendPlayerMessage(p, "§7Das §eKonto §7des §7Spielers §e" + target.getName() + " §7wurde um §e" + amount + " §7erhöht");
            break;
        }
        return true;
    }
}
