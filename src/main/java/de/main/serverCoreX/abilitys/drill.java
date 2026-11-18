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
        Location startLoc = e.getBlock().getLocation();

        Location loc = p.getLocation();

        float yaw = loc.getYaw();     // X Z
        float pitch = loc.getPitch();  // Y

        if (p.isOp())
            p.sendMessage("Yaw: " + yaw + " | Pitch: " + pitch);

        if (pitch > 60)
        {
            // Startet bei -1 und geht auf 1
            // Währendessen geht das selbe pro durchlauf bei der Z-Achse, es wird der Block entfernt
            for (int x = -1; x <= 1; x++)
                for (int z =-1; z<= 1; z++)
                    startLoc.clone().add(x,0,z).getBlock().setType(Material.AIR);

            if (p.isOp())
                p.sendMessage("Nach unten mit dir!");
            return;
        }

        if (yaw >= 51 && yaw <= 160 || yaw >= -160 && yaw <= -51)
        {
            for (int z =-1; z <=1; z++)
                for (int y =-1; y <=1; y++)
                    startLoc.clone().add(0,y,z).getBlock().setType(Material.AIR);

            return;
        }


        // Geht genau nur bis zu 1
        for (int x=-1; x <=1; x++)
            for (int y=-1; y <=1; y++)
                startLoc.clone().add(x,y,0).getBlock().setType(Material.AIR);





        p.sendMessage("Drill erkannt!");

    }
}
