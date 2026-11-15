package de.main.serverCoreX.commands.settings;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.commands.settings.settingGUI.openGUI;

public class setting implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("settings"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        openGUI(p);
        return true;
    }
}
