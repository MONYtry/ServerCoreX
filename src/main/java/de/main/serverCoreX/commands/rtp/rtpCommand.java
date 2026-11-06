package de.main.serverCoreX.commands.rtp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.commands.rtp.rtpGUI.getGUI;

public class rtpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("rtp"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        p.openInventory(getGUI());
        return true;
    }
}
