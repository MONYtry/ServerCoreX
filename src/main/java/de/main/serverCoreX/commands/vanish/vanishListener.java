package de.main.serverCoreX.commands.vanish;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import java.util.HashSet;
import java.util.Set;
import static de.main.serverCoreX.commands.vanish.vanishManager.getVanishedPlayer;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class vanishListener implements Listener {

    public static final Set<String> vanishedChests = new HashSet<>();
    String path = "vanish";

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();

        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        if (!p.hasPermission(path + ".bypass"))
            return;

        e.setCancelled(true);
        sendPlayerMessage(p,"§7Im §eVanish §7darfst du dies §enicht §7tun!");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();

        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        if (!p.hasPermission(path + ".bypass"))
            return;

        e.setCancelled(true);
        sendPlayerMessage(p,"§7Im §eVanish §7darfst du dies §enicht §7tun!");
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();
        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        if (!p.hasPermission(path + ".bypass"))
            return;

        e.setCancelled(true);
        sendPlayerMessage(p,"§7Du darfst das nicht im §eVanish");
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player p))
            return;

        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        e.setCancelled(true);
        sendPlayerMessage(p,"§7Du darfst das nicht im §eVanish");
    }

    @EventHandler
    public void onChestOpen(InventoryOpenEvent e) {

        Player p = (Player) e.getPlayer();

        // Falls der Nutzer der die Interaktion ausruft nicht im Vanish ist, abbruch
        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        if (!p.hasPermission(path + ".bypass"))
            return;

        // Falls das Inventar keine Kiste war, abbruch
        if (!(e.getInventory().getHolder() instanceof Chest chest))
            return;

        // Holt sich den Block der geöffnet wurde
        Block block = chest.getBlock();

        // Fügt in den String Werte hinzu
        vanishedChests.add(
                block.getWorld().getName() + ":" +
                        block.getX() + ":" +
                        block.getY() + ":" +
                        block.getZ()
        );
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (!getVanishedPlayer(p.getUniqueId()))
            return;

        if (!(e.getInventory().getHolder() instanceof Chest chest))
            return;

        if (!p.hasPermission(path + ".bypass"))
            return;

        Block block = chest.getBlock();

        vanishedChests.remove(
                block.getWorld().getName() + ":" +
                        block.getX() + ":" +
                        block.getY() + ":" +
                        block.getZ()
        );
    }
}
