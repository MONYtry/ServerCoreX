package de.main.serverCoreX.commands;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.helper.messages.*;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("reload"))
            return true;
        if (!(sender instanceof Player p))
            return true;

        FileConfiguration file = getMessages();

        if (saveAll())
        {
            sendPlayerMessage(p,file.getString(getPath() + "reload_succses"));
        }
        else
            sendPlayerMessage(p,file.getString(getPath() + "reload_error"));

        return true;
    }

    public static boolean saveAll() {
        try
        {
            ServerCoreX.getInstance().reloadAll();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}