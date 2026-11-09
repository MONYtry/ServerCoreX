package de.main.serverCoreX.commands.rtp;

import de.main.serverCoreX.ServerCoreX;
import de.main.serverCoreX.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import static de.main.serverCoreX.helper.messages.getPrefix;


public class rtpGUI {

    public static Inventory getGUI()
    {
        Inventory gui = Bukkit.createInventory(null,36,getPrefix() + "§eFarmwelten");
        ItemCreator.autoGenItemsWithConfigGUI(gui, ServerCoreX.getInstance().gui,null);
        return gui;
    }
}
