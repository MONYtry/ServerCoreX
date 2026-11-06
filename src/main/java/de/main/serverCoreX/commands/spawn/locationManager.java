package de.main.serverCoreX.commands.spawn;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class locationManager {

    public static Location getLocation(String key)
    {
        FileConfiguration config = ServerCoreX.getInstance().locations;
        return config.getLocation("locations." + key);
    }

    public static void saveLocation(String key, Location loc) {
        FileConfiguration config = ServerCoreX.getInstance().locations;

        config.set("locations." + key, loc);

        ServerCoreX.getInstance().saveLocations();
    }
}
