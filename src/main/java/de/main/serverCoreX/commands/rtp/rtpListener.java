package de.main.serverCoreX.commands.rtp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class rtpListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        String titel = e.getView().getTitle();
        if (!titel.equalsIgnoreCase("§eFarmwelten-Auswahl"))
            return;
        e.setCancelled(true);
    }
}
