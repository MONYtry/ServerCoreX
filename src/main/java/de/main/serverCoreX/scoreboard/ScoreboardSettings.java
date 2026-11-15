package de.main.serverCoreX.scoreboard;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class ScoreboardSettings {

    public static FileConfiguration getFile()
    {
        return ServerCoreX.getInstance().settings;
    }

    public static void toggleCustomScoreboard(UUID uuid)
    {
        FileConfiguration file = getFile();
        boolean getState = file.getBoolean("scoreboard." + uuid.toString());
        if (getState)
            file.set("scoreboard." + uuid.toString(),null);
        else
            file.set("scoreboard." +  uuid.toString(), true);

        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);

        Player p = Bukkit.getPlayer(uuid);
        if (p == null)
            return;
        String displayStatus = getState ? "§cdeaktiviert!" : "§aaktiviert!";
        sendPlayerMessage(p,"§7Es wurde §eerfolgreich §7das §eCustom-Scoreboard " + displayStatus);
        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
        p.closeInventory();
    }

    public static boolean getCustomScoreboard(UUID uuid)
    {
        return getFile().contains("scoreboard." + uuid.toString());
    }

    public static void removeFromCustomScoreboard(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("scoreboard." + uuid.toString(), null);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }

    public static void addToCustomScoreboard(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("scoreboard." + uuid.toString(), true);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }
}
