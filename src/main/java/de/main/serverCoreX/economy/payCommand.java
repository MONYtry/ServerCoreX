package de.main.serverCoreX.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static de.main.serverCoreX.economy.EconomyManager.addBalance;
import static de.main.serverCoreX.economy.EconomyManager.removeFromBalance;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class payCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("pay"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (args.length < 2)
        {
            sendPlayerMessage(p, "§cZu wenig Argumente Nutze /pay (Name) (Anzahl)");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            sendPlayerMessage(p,"§7Der angegebene §eSpieler §7konnte nicht §egefunden §7werden!");
            return true;
        }

        if (target == p)
        {
            sendPlayerMessage(p,"§7Du kannst Dir nicht selber §eGeld §7senden!");
            return true;
        }

        double amount = 0;
        try
        {
            amount = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        if (!removeFromBalance(p.getUniqueId(),amount))
        {
            sendPlayerMessage(p, "§7Du hast §enicht genug Guthaben");
            return true;
        }

        addBalance(target.getUniqueId(),amount);
        sendPlayerMessage(target, "§e" + p.getName() + "§7 hat dir §e" + amount + "$ §7gesendet");

        sendPlayerMessage(p,"§7Du hast erfolgreich §e" + amount + "$ §7an §e" + target.getName() + "§7 gesendet");
        return true;
    }
}
