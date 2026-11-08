package de.main.serverCoreX.commands.rtp;

import de.main.serverCoreX.ServerCoreX;
import de.main.serverCoreX.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;


public class rtpGUI {

    public static Inventory getGUI()
    {
        Inventory gui = Bukkit.createInventory(null,36,"§eFarmwelten-Auswahl");
        ItemCreator.autoGenItemsWithConfigGUI(gui, ServerCoreX.getInstance().gui);
        return gui;
    }
}
