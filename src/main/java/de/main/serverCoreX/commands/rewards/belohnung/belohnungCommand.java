package de.main.serverCoreX.commands.rewards.belohnung;

import de.main.serverCoreX.economy.EconomyManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

import static de.main.serverCoreX.commands.rewards.belohnung.belohnungsManager.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class belohnungCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("belohnung"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        UUID uuid = p.getUniqueId();

        if (getAlreadyCollected(uuid))
        {
            long remaining = getRemainingTime(uuid);
            long minutes = remaining / 60000;
            long seconds = (remaining % 60000) / 1000;

            sendPlayerMessage(p, "§7Du kannst deine Belohnung in §e" + minutes + "m " + seconds + "s §7wieder abholen!");
            return true;
        }

        setAlreadyCollected(uuid);
        sendPlayerMessage(p,"§7Die tägliche Belohnung wurde nun abgeholt");
        sendPlayerMessage(p,"§7Du hast §e250$ §7erhalten");
        EconomyManager.addBalance(uuid, 250);
        return true;
    }
}
