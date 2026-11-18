package de.main.serverCoreX.commands.customDB;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

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
        Player p = (Player) e.getWhoClicked();

        p.getInventory().addItem(item);
        sendPlayerMessage(p, "§7Dir wurde erfolgreich §e" + item.getItemMeta().getDisplayName() + " §7geben!");
        p.closeInventory();
    }
}
