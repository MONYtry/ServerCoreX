package de.main.serverCoreX.commands.rtp;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static de.main.serverCoreX.commands.rtp.rtp.handleRTP;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class rtpListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        String titel = e.getView().getTitle();
        if (!titel.equalsIgnoreCase("§eFarmwelten-Auswahl"))
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
        switch (action.toLowerCase())
        {
            case "rtp_overworld":
                // RTP in overworld
                handleRTP(p);
                break;

            case "close":
                // RTP in overworld
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1,1);
                break;


        }
    }
}
