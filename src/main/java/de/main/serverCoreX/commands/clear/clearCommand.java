package de.main.serverCoreX.commands.clear;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class clearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("clear"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (!hasPermission(p,"clear.use"))
            return true;

        if (args.length == 0)
        {
            p.getInventory().clear();
            sendPlayerMessage(p,"§7Dein §eInventar §7wurde erfolgreich §egelöscht");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            sendPlayerMessage(p,"§7Der angebene Spieler konnte nicht gefunden werden!");
            return true;
        }

        if (target == p)
        {
            p.getInventory().clear();
            sendPlayerMessage(p,"§7Dein §eInventar §7wurde erfolgreich §egelöscht");
            return true;
        }

        sendPlayerMessage(p,"§7Das Inventar von §e" + target.getName() + " §7wurde erfolgreich §egelöscht!");
        sendPlayerMessage(target,"§7Dein §eInventar §7wurde erfolgreich von §e" + p.getName() + " §egelöscht");

        target.getInventory().clear();
        return true;
    }
}
