package de.main.serverCoreX.commands.invsee;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static de.main.serverCoreX.helper.messages.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.timer.repeat;

public class invseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!command.getName().equalsIgnoreCase("invsee"))
            return true;

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Diesen Befehl darf nur ein Spieler ausführen!");
            return true;
        }

        if (!p.hasPermission("invsee.use")) {
            sendPlayerMessage(p, getCommandNotExist());
            return true;
        }

        if (args.length < 1) {
            sendPlayerMessage(p, getCommandNotExist());
            return true;
        }

        // Zielspieler bestimmen
        Player target;
        target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            FileConfiguration file = getMessages();

            sendPlayerMessage(p,file.getString(getPath() + "player_not_online"));
            return true;
        }

        if (target == p)
        {
            sendPlayerMessage(p,"§7Du kannst dein eigenes §eInventar §7nicht §eeinsehen");
            return true;
        }

        if (!p.hasPermission("invsee.take")) {
            Inventory inv = Bukkit.createInventory(null, 45, "§8Invsee von §e" + target.getName());

            inv.setContents(target.getInventory().getContents());
            p.openInventory(inv);

            new BukkitRunnable() {
                @Override
                public void run() {

                    if (!p.isOnline() || !p.getOpenInventory().getTopInventory().equals(inv)) {
                        cancel();
                        return;
                    }
                    inv.setContents(target.getInventory().getContents());
                }
            }.runTaskTimer(ServerCoreX.getInstance(), 40L, 40L);

            return true;
        }

        p.openInventory(target.getInventory());
        return true;
    }
}
