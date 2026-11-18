package de.main.serverCoreX.commands.customDB;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.spec.NamedParameterSpec;
import java.util.ArrayList;
import java.util.List;

import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class customItemDBListener implements Listener {

    private static final Logger log = LoggerFactory.getLogger(customItemDBListener.class);

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

        NamespacedKey configKey = new NamespacedKey(ServerCoreX.getInstance(),"config_key");
        String configItem = meta.getPersistentDataContainer().get(configKey, PersistentDataType.STRING);

        if (configItem == null)
            return;

        List<String> lore = meta.getLore();
        if (lore == null)
            lore = new ArrayList<>();

        // Holt sich Config, erstellt ein Path-String, holt sich Liste aus der Config
        FileConfiguration cfg = ServerCoreX.getInstance().getConfig();
        String path = "items." + configItem;
        List<String> description = cfg.getStringList(path + ".description");

        // Entfernt die Display-Beschreibung des Custom-Items-GUI
        if (lore != null)
            lore.removeAll(description);

        meta.setLore(lore);
        item.setItemMeta(meta);

        p.getInventory().addItem(item);
        sendPlayerMessage(p, "§7Dir wurde erfolgreich §e" + item.getItemMeta().getDisplayName() + " §7geben!");
        p.closeInventory();
    }
}
