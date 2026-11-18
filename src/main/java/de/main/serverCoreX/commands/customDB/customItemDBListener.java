package de.main.serverCoreX.commands.customDB;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

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
        if (!hasPermission(p,"customItems.use"))
            return;

        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        lore.remove("§aFähigkeit: Zerstört 3x3 Blöcke");

        meta.setLore(lore);
        item.setItemMeta(meta);

        p.getInventory().addItem(item);
        sendPlayerMessage(p, "§7Dir wurde erfolgreich §e" + item.getItemMeta().getDisplayName() + " §7geben!");
        p.closeInventory();
    }
}
