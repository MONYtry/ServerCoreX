package de.main.serverCoreX.economy.bank;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.main.serverCoreX.helper.ItemCreator.autoGenItemsWithConfigGUI;
import static de.main.serverCoreX.helper.messages.getPrefix;

public class bankGUI {

    public static void createBankGUI(Player p)
    {
        Inventory inv = Bukkit.createInventory(null,45,getPrefix() + "§cBank");
        FileConfiguration file = ServerCoreX.getInstance().bank;

        autoGenItemsWithConfigGUI(inv, file,p);
        p.openInventory(inv);
    }
}
