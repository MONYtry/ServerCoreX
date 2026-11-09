package de.main.serverCoreX.commands.rewards.daily;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class dailyManager {

    public static final long COOLDOWN = 24 * 60 * 60 * 1000L; // 24 Stunden

    public static FileConfiguration getFile() {
        return ServerCoreX.getInstance().PlayerData;
    }

    private static String getPath(UUID uuid) {
        return "dailyCollected." + uuid;
    }

    public static boolean getAlreadyCollected(UUID uuid) {
        long lastCollected = getFile().getLong(getPath(uuid));

        if (lastCollected == 0) {
            return false;
        }

        return System.currentTimeMillis() - lastCollected < COOLDOWN;
    }

    public static void setAlreadyCollected(UUID uuid) {
        getFile().set(getPath(uuid), System.currentTimeMillis());
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().PlayerDataFile, ServerCoreX.getInstance().PlayerData);
    }

    public static long getRemainingTime(UUID uuid) {
        long lastCollected = getFile().getLong(getPath(uuid));

        long remaining = (lastCollected + COOLDOWN)
                - System.currentTimeMillis();

        return Math.max(0, remaining);
    }
}