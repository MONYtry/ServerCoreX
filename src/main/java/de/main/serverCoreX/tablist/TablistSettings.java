package de.main.serverCoreX.tablist;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class TablistSettings {

    public static FileConfiguration getFile()
    {
        return ServerCoreX.getInstance().settings;
    }

    public static void toggleCustomTablist(UUID uuid)
    {
        FileConfiguration file = getFile();
        boolean getState = file.getBoolean("tablist." + uuid.toString());
        if (getState)
            file.set("tablist." + uuid.toString(),null);
        else
            file.set("tablist." +  uuid.toString(), true);

        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);

        Player p = Bukkit.getPlayer(uuid);
        if (p == null)
            return;
        String displayStatus = getState ? "§cdeaktiviert!" : "§aaktiviert!";
        sendPlayerMessage(p,"§7Es wurde §eerfolgreich §7das §eCustom-tablist " + displayStatus);
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
        p.closeInventory();
    }

    public static boolean getCustomtablist(UUID uuid)
    {
        return getFile().contains("tablist." + uuid.toString());
    }

    public static void removeFromCustomtablist(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("tablist." + uuid.toString(), null);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }

    public static void addToCustomtablist(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("tablist." + uuid.toString(), true);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }
}
