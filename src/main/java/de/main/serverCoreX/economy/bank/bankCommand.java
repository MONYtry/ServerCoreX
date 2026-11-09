package de.main.serverCoreX.economy.bank;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.economy.bank.bankGUI.createBankGUI;

public class bankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("bank"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        createBankGUI(p);
        return true;
    }
}
