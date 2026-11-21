
package de.main.serverCoreX.abilitys;

import de.main.serverCoreX.ServerCoreX;
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

public class timber implements Listener {

    static List<Material> timber_whitelist = new ArrayList<>();

    static {
        timber_whitelist.add(Material.OAK_LOG);
    }

    @EventHandler
    public void blockbroken(BlockBreakEvent e) {

        Player p = e.getPlayer();
        ItemStack itemStack = p.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR)
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return;

        NamespacedKey key = new NamespacedKey(
                ServerCoreX.getPlugin(ServerCoreX.class),
                "custom_item"
        );

        String value = itemMeta.getPersistentDataContainer()
                .get(key, PersistentDataType.STRING);

        if (value == null || !value.equalsIgnoreCase("timber"))
            return;

        Block block = e.getBlock();

        if (!timber_whitelist.contains(block.getType()))
            return;

        e.setCancelled(true);

        // Ersten Block abbauen
        block.getDrops(itemStack).forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
        block.setType(Material.AIR);

        // Maximal 20 Blöcke darüber abbauen
        for (int i = 0; i < 15; i++) {

            Block above = block.getRelative(0, i + 1, 0);

            if (!timber_whitelist.contains(above.getType()))
                break;

            above.getDrops(itemStack).forEach(drop -> above.getWorld().dropItemNaturally(above.getLocation(), drop));
            above.setType(Material.AIR);

            itemStack.damage(1, p);

            if (itemStack.getType() == Material.AIR)
                break;
        }
    }
}
