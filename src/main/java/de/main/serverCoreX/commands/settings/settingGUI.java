package de.main.serverCoreX.commands.settings;

import de.main.serverCoreX.ServerCoreX;
import de.main.serverCoreX.helper.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class settingGUI {

    public static void openGUI(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 36, "§cEinstellungen");
        ItemCreator.autoGenItemsWithConfigGUI(inv, ServerCoreX.getInstance().settingsGUI,p);

        p.openInventory(inv);
    }
}
