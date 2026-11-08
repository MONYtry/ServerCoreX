package de.main.serverCoreX.helper;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class messages {


    public static void sendPlayerMessage(Player p,String message)
    {
        p.sendMessage(getPrefix() + message);
    }

    public static FileConfiguration getMessages()
    {
       return JavaPlugin.getPlugin(ServerCoreX.class).messages;
    }
    public static String getPath()
    {
        return "messages.";
    }

    public static String getPrefix()
    {
        FileConfiguration messages = getMessages();
        String prefix = messages.getString(getPath() + "prefix");
        return prefix;
    }
    public static String getFlightEnabled()
    {
        FileConfiguration messages = getMessages();
        String flightEnabled = messages.getString(getPath() + "fly_enabled");
        return  flightEnabled;
    }

    public static String getFlightDisabled()
    {
        FileConfiguration messages = getMessages();
        String flightDisabled = messages.getString(getPath() + "fly_disabled");
        return  flightDisabled;
    }

    public static String getCommandNotExist()
    {
        String commandDoesNotExist = getMessages().getString(getPath() + "command_does_not_exist");
        return commandDoesNotExist;
    }
    public static String getNoPerm()
    {
        FileConfiguration messages = getMessages();
        String missing_permission = messages.getString(getPath() + "missing_permission");
        return missing_permission;
    }
}
