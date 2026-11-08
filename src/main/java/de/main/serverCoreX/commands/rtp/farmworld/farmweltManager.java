package de.main.serverCoreX.commands.rtp.farmworld;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.FileConfiguration;

public class farmweltManager {

    public static FileConfiguration getConfig()
    {
        return ServerCoreX.getInstance().getConfig();
    }
    public static String getPath()
    {
        String path = "farmwelt.";
        return path;
    }
    public static String getFarmworldName()
    {
        FileConfiguration file = getConfig();
        String farmworldName = file.getString(getPath() + "name","Farmwelt");
        return farmworldName;
    }

    public static boolean getIsRedstoneEnabled()
    {
        FileConfiguration file = getConfig();
        return file.getBoolean(getPath() + "redstoneStatus");
    }
}
