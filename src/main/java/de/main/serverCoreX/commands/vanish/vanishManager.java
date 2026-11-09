package de.main.serverCoreX.commands.vanish;
import java.util.HashSet;
import java.util.UUID;

public class vanishManager
{
    public static HashSet<UUID> vanishedPlayers = new HashSet<>();

    public static HashSet<UUID> getVanishedPlayers()
    {
        return vanishedPlayers;
    }

    public static void addToVanishedPlayers(UUID uuid)
    {
        vanishedPlayers.add(uuid);
    }

    public static void removeFromVanishedPlayers(UUID uuid)
    {
        vanishedPlayers.remove(uuid);
    }

    public static boolean getVanishedPlayer(UUID uuid)
    {
        if (vanishedPlayers.contains(uuid))
            return true;
        return false;
    }
}
