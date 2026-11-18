package de.main.serverCoreX.abilitys;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class drill implements Listener {

    @EventHandler
    public void blockbroken (BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        if (p == null)
            return;

        ItemStack itemStack = p.getInventory().getItemInMainHand();
        if (itemStack == null)
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class),"custom_item");

        String value = itemMeta.getPersistentDataContainer().get(
                key,
                PersistentDataType.STRING
        );
        if (value == null)
            return;
        if (!value.equalsIgnoreCase("drill"))
            return;

        e.setCancelled(true);
        Location loc = e.getBlock().getLocation();
        Location startLoc = loc;

        Block block = startLoc.clone().getBlock();
        Block rightBlock = startLoc.clone().add(-1,0,0).getBlock();
        Block leftBlock = startLoc.clone().add(1,0,0).getBlock();

        Block topBlock = startLoc.clone().add(0,1,0).getBlock();
        Block bottomBlock = startLoc.clone().add(0,-1,0).getBlock();

        Block upperLeftBlock = startLoc.clone().add(1,1,0).getBlock();
        Block bottomLeftBlock = startLoc.clone().add(1,-1,0).getBlock();

        Block upperRightBlock = startLoc.clone().add(-1,1,0).getBlock();
        Block bottomRightBlock = startLoc.clone().add(-1,-1,0).getBlock();

        block.setType(Material.AIR);
        rightBlock.setType(Material.AIR);
        leftBlock.setType(Material.AIR);
        topBlock.setType(Material.AIR);
        bottomBlock.setType(Material.AIR);
        upperLeftBlock.setType(Material.AIR);
        bottomLeftBlock.setType(Material.AIR);
        bottomRightBlock.setType(Material.AIR);
        upperRightBlock.setType(Material.AIR);





        p.sendMessage("Drill erkannt!");

    }
}
