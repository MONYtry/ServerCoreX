package de.main.serverCoreX.commands.clearchat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class clearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("clearchat"))
            return true;

        if (!(sender instanceof Player p))
        {
            for (int i = 0; i < 100; i++)
            {
               Bukkit.broadcastMessage("");
            }

            Bukkit.broadcastMessage(getPrefix());
            Bukkit.broadcastMessage(getPrefix() + "§7Der §eChat §7wurde §egeleert!");
            Bukkit.broadcastMessage(getPrefix());
            return true;
        }

        if (!hasPermission(p,"clearchat.use"))
            return true;

        for (int i = 0; i < 100; i++)
        {
            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage(getPrefix());
        Bukkit.broadcastMessage(getPrefix() + "§7Der §eChat §7wurde §egeleert!");
        Bukkit.broadcastMessage(getPrefix());
        return true;
    }
}
