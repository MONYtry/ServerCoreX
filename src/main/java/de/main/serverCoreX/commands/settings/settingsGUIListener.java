package de.main.serverCoreX.commands.settings;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.prefix.PrefixSettings.toggleCustomPrefix;
import static de.main.serverCoreX.scoreboard.ScoreboardSettings.toggleCustomScoreboard;
import static de.main.serverCoreX.tablist.TablistSettings.toggleCustomTablist;

public class settingsGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        String titel = e.getView().getTitle();
        if (!titel.equalsIgnoreCase("§cEinstellungen"))
            return;
        e.setCancelled(true);

        if (e.getCurrentItem() == null)
            return;

        ItemStack item = e.getCurrentItem();
        if (!item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();

        // Holt sich die Action
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(ServerCoreX.getInstance(), "action"), PersistentDataType.STRING);
        if (action == null)
            return;

        Player p = (Player) e.getWhoClicked();

        switch (action.toLowerCase()) {
            case "scoreboard":
                toggleCustomScoreboard(p.getUniqueId());
                break;

            case "prefix":
                toggleCustomPrefix(p.getUniqueId());
                break;

            case "tablist":
                toggleCustomTablist(p.getUniqueId());
                break;

            case "close":
                p.closeInventory();
                break;

            default:
                sendPlayerMessage(p, "§cNicht gefunden!");
                break;
        }
    }
}
