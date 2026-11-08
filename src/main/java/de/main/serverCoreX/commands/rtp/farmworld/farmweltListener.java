package de.main.serverCoreX.commands.rtp.farmworld;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import static de.main.serverCoreX.commands.rtp.farmworld.farmweltManager.getFarmworldName;
import static de.main.serverCoreX.commands.rtp.farmworld.farmweltManager.getIsRedstoneEnabled;

public class farmweltListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockRedstoneEvent e) {
        if (getIsRedstoneEnabled())
            return;
        if (e.getBlock().getWorld().getName().equalsIgnoreCase(getFarmworldName())) {
            e.setNewCurrent(0);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        if (e.getCause() == EntityDamageEvent.DamageCause.CONTACT)
        {
            if (e.getEntity() instanceof Player)
                e.setCancelled(true);
        }
    }
}
