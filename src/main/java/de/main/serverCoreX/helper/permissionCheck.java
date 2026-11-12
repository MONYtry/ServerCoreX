package de.main.serverCoreX.helper;

import org.bukkit.entity.Player;

import static de.main.serverCoreX.helper.messages.getNoPerm;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class permissionCheck {
    public static boolean hasPermission(Player p, String permission)
    {
        if (!p.hasPermission(permission))
        {
            sendPlayerMessage(p,getNoPerm());
            return false;
        }
        return true;
    }
}
