package de.main.serverCoreX.commands.rewards.belohnung;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class belohnungsManager {

    public static final long COOLDOWN = 5 * 60 * 1000L;

    public static FileConfiguration getFile() {
        return ServerCoreX.getInstance().PlayerData;
    }

    private static String getPath(UUID uuid) {
        return "rewardCollected." + uuid;
    }

    public static boolean getAlreadyCollected(UUID uuid) {
        long lastCollected = getFile().getLong(getPath(uuid));

        return lastCollected != 0 &&
                System.currentTimeMillis() - lastCollected < COOLDOWN;
    }

    public static void setAlreadyCollected(UUID uuid) {
        getFile().set(getPath(uuid), System.currentTimeMillis());

        ServerCoreX.getInstance().saveFile(
                ServerCoreX.getInstance().PlayerDataFile,
                ServerCoreX.getInstance().PlayerData
        );
    }

    public static long getRemainingTime(UUID uuid) {
        long lastCollected = getFile().getLong(getPath(uuid));

        return Math.max(0,
                lastCollected + COOLDOWN - System.currentTimeMillis());
    }
}