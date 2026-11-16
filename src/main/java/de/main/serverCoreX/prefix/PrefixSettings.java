package de.main.serverCoreX.prefix;
import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class PrefixSettings {

    public static FileConfiguration getFile()
    {
        return ServerCoreX.getInstance().settings;
    }

    public static void toggleCustomPrefix(UUID uuid)
    {
        FileConfiguration file = getFile();
        boolean getState = file.getBoolean("prefix." + uuid.toString());
        if (getState)
            file.set("prefix." + uuid.toString(),null);
        else
            file.set("prefix." +  uuid.toString(), true);

        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);

        Player p = Bukkit.getPlayer(uuid);

        if (p == null)
            return;

        String displayStatus = getState ? "§cdeaktiviert!" : "§aaktiviert!";
        sendPlayerMessage(p,"§7Es wurde §eerfolgreich §7das §eCustom-prefix " + displayStatus);
        sendPlayerMessage(p,"§7Du musst dem §eServer erneut §7beitretten damit die §eÄnderungen §7angezeigt werden!");

        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK,1,1);
        p.closeInventory();
    }

    public static boolean getCustomPrefix(UUID uuid)
    {
        return getFile().contains("prefix." + uuid.toString());
    }

    public static void removeFromCustomScoreboard(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("prefix." + uuid.toString(), null);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }

    public static void addToCustomScoreboard(UUID uuid)
    {
        FileConfiguration file = getFile();
        file.set("prefix." + uuid.toString(), true);
        ServerCoreX.getInstance().saveFile(ServerCoreX.getInstance().getSettingsFile(), file);
    }
}
