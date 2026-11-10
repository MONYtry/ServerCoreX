package de.main.serverCoreX.listener.joinLeave;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static de.main.serverCoreX.helper.messages.getPrefix;

public class firstJoinManager {

    public static FileConfiguration getConfig()
    {
        return ServerCoreX.getInstance().PlayerData;
    }

    public static String getPath()
    {
        String path = "joinCount";
        return path;
    }

    public static void addFirstJoin(Player p)
    {
        FileConfiguration cfg = getConfig();
        String path = getPath();

        int joinCount = cfg.getInt(path);
        File file = ServerCoreX.getInstance().getPlayerDataFile();

        joinCount += 1;
        cfg.set(path,joinCount);
        Bukkit.getLogger().info("Neuer join: " + joinCount);
        Bukkit.broadcastMessage(getPrefix() + "§7Willkommen §e" + p.getName() + " §7auf §6§lBLOCKHIVE! §7[#" + joinCount + "]");
        try
        {
            cfg.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
