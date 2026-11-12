package de.main.serverCoreX.economy.bank;

import de.main.serverCoreX.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class moneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("money"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        if (!hasPermission(p,"money.use"))
            return true;

        if (args.length == 0)
        {
            NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
            double money = EconomyManager.getBalance(p.getUniqueId());
            String formattedAmount = format.format(money);

            sendPlayerMessage(p,"§7Dein §eKontostand §7beträgt §e" + formattedAmount + "$");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            sendPlayerMessage(p,"§7Der angebene §eSpieler §7konnte §enicht gefunden §7werden");
            return true;
        }
        NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
        double money = EconomyManager.getBalance(target.getUniqueId());
        String formattedAmount = format.format(money);

        sendPlayerMessage(p,"§7Der §eKontostand §7von §e" + target.getName() +" §7beträgt §e" + formattedAmount + "$");
        return true;
    }
}
