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

import java.util.ArrayList;
import java.util.List;

public class drill implements Listener {
    static List<Material> drill_whitelist = new ArrayList<>();
    static {
        drill_whitelist.add(Material.STONE);
        drill_whitelist.add(Material.GRANITE);
        drill_whitelist.add(Material.ANDESITE);
        drill_whitelist.add(Material.DEEPSLATE);
    }


    @EventHandler
    public void blockbroken (BlockBreakEvent e)
    {
        Player p = e.getPlayer();
        if (p == null)
            return;

        ItemStack itemStack = p.getInventory().getItemInMainHand();
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return;
        if (!itemStack.hasItemMeta())
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class),"custom_item");

        String value = itemMeta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (value == null)
            return;

        if (!value.equalsIgnoreCase("3x3_drill") && !value.equalsIgnoreCase("5x5_drill"))
            return;

        int start;
        int end;

        switch (value.toLowerCase())
        {
            case "3x3_drill":
                start = -1;
                end = 1;
                break;

            case "5x5_drill":
                start = -2;
                end = 2;
                break;
            default:
                start = -1;
                end = 1;
                break;
        }


        e.setCancelled(true);
        Location startLoc = e.getBlock().getLocation();

        Location loc = p.getLocation();
        float yaw = loc.getYaw();     // X Z
        float pitch = loc.getPitch();  // Y
        boolean blockBroken = false;

        if (pitch >= 60 || pitch <= -60)
        {
            // Startet bei -1 und geht auf 1
            // Währendessen geht das selbe pro durchlauf bei der Z-Achse, es wird der Block entfernt
            for (int x = start; x <= end; x++)
                for (int z = start; z <= end; z++)
                    if (drill_whitelist.contains(startLoc.clone().add(x,0,z).getBlock().getType()))
                    {
                        Block block = startLoc.clone().add(x, 0, z).getBlock();

                        block.getDrops(itemStack).forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                        startLoc.clone().add(x,0,z).getBlock().setType(Material.AIR);

                        blockBroken = true;
                    }

            if (blockBroken)
                itemStack.damage(3,p);
            return;
        }

        if (yaw >= 51 && yaw <= 160 || yaw >= -160 && yaw <= -51)
        {
            for (int z = start; z <= end; z++)
                for (int y = start; y <= end; y++)
                    if (drill_whitelist.contains(startLoc.clone().add(0,y,z).getBlock().getType()))
                    {
                        Block block = startLoc.clone().add(0, y, z).getBlock();

                        block.getDrops(itemStack).forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                        startLoc.clone().add(0,y,z).getBlock().setType(Material.AIR);

                        blockBroken = true;
                    }

            if (blockBroken)
                itemStack.damage(3,p);
            return;
        }


        // Geht genau nur bis zu 1
        for (int x= start; x <= end; x++)
            for (int y= start; y <= end; y++)
                if (drill_whitelist.contains(startLoc.clone().add(x,y,0).getBlock().getType()))
                {
                    Block block = startLoc.clone().add(x, y, 0).getBlock();

                    block.getDrops(itemStack).forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
                    startLoc.clone().add(x, y, 0).getBlock().setType(Material.AIR);

                    blockBroken = true;
                }
        if (blockBroken)
            itemStack.damage(3,p);
    }
}
