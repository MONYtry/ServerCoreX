package de.main.serverCoreX.kit;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class kitManager {
    public static HashSet<UUID> collectedKit = new HashSet<>();

    public static FileConfiguration getConfig()
    {
        return ServerCoreX.getInstance().kitPlayerData;
    }

    public static String getPath()
    {
        String path = "starterKitCollectedBy";
        return path;
    }
    public static void addToCollectedKit(UUID uuid)
    {
        FileConfiguration cfg = getConfig();
        if (cfg == null)
        {
            Bukkit.getLogger().warning("Es ist ein kritischer Fehler beim Speichern der Starterkit-Nutzer aufgetreten!");
            Bukkit.getLogger().warning("Überprüfe ob die Datei vorhanden!");
            return;
        }

        String path = getPath();
        if (path == null)
        {
            Bukkit.getLogger().warning("Es ist ein kritischer Fehler beim Speichern der Starterkit-Nutzer aufgetreten!");
            Bukkit.getLogger().warning("Überprüfe ob die Datei vorhanden ist und ob Schreibfehler existieren!");
            return;
        }
        String user = uuid.toString();
        cfg.set(path + "." + user, true);

        File file = ServerCoreX.getInstance().getKitPlayerDataFile();
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("Es wurde ein Starterkit abgeholt von: " + uuid);
        Bukkit.getLogger().info("Gespeichert: " + cfg.getString(path + "." + user));
    }
    public static boolean getAlreadyCollectedKit(UUID uuid)
    {
        FileConfiguration cfg = getConfig();
        if (cfg == null)
        {
            Bukkit.getLogger().warning("Config konnte nicht geladen werden!");
            return false;
        }

        String path = getPath();
        if (path == null)
        {
            Bukkit.getLogger().warning("Config-Pfad ist null!");
            return false;
        }

        String user = uuid.toString();
        if (cfg.getBoolean(path + "." + user))
            return true;
        return false;
    }
}
