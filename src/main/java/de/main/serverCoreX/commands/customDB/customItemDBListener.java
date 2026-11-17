package de.main.serverCoreX.commands.customDB;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.main.serverCoreX.helper.messages.getPrefix;

public class customItemDBListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String titel = e.getView().getTitle();
        if (!titel.equalsIgnoreCase(getPrefix() + "§cCustom-Items"))
            return;
        e.setCancelled(true);

        if (e.getCurrentItem() == null)
            return;

        ItemStack item = e.getCurrentItem();
        if (!item.hasItemMeta())
            return;
    }
}
