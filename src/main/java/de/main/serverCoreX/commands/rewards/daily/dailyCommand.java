package de.main.serverCoreX.commands.rewards.daily;

import de.main.serverCoreX.economy.EconomyManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static de.main.serverCoreX.commands.rewards.daily.dailyManager.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class dailyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p))
            return true;

        UUID uuid = p.getUniqueId();
        if (getAlreadyCollected(uuid)) {

            long remaining = getRemainingTime(uuid);

            long hours = remaining / 3600000;
            long minutes = (remaining % 3600000) / 60000;
            long seconds = (remaining % 60000) / 1000;

            sendPlayerMessage(p, "§7Du kannst deine Belohnung in §e" + hours + "h " + minutes + "m " + seconds + "s §7wieder abholen!");
            return true;
        }

        setAlreadyCollected(uuid);
        sendPlayerMessage(p, "§7Die tägliche Belohnung wurde abgeholt!");
        sendPlayerMessage(p, "§7Du hast §e250$ §7erhalten");
        EconomyManager.addBalance(uuid, 250);
        return true;
    }
}