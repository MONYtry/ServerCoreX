package de.main.serverCoreX.commands.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class broadcast implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("broadcast"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (args.length < 2)
        {
            sendPlayerMessage(p, "§7Deine Eingabe war falsch benutze (/broadcast <Message>");
            return true;
        }

        for (Player target : Bukkit.getOnlinePlayers())
        {
            target.sendTitle(args[0],args[1]);
        }
        sendPlayerMessage(p, "§eBroadcast §7wurde erfolgreich §eausgeführt");
        return true;
    }
}
