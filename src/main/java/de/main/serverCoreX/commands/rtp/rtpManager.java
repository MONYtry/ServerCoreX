package de.main.serverCoreX.commands.rtp;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class rtpManager {
    public static LinkedHashMap<UUID, Integer> randomteleportQeues = new LinkedHashMap<>();
    public static HashMap<UUID, Integer> rtpTask = new HashMap<>();

    public static Integer getTime(UUID uuid)
    {
        return randomteleportQeues.get(uuid);
    }

    public static boolean getIsInQeue(UUID uuid)
    {
        return randomteleportQeues.containsKey(uuid);
    }

    public static void addInQeue(UUID uuid)
    {
        randomteleportQeues.put(uuid,20);
    }
    public static void removeOfQeue(UUID uuid)
    {
        randomteleportQeues.remove(uuid);
    }

    public  static Integer getPosition(UUID uuid)
    {
        int position = 1;

        for (UUID id : randomteleportQeues.keySet())
        {
            if (id.equals(uuid)){
                return position;
            }
            position++;
        }
        return -1;
    }
}
