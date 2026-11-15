package de.main.serverCoreX.commands.itemedit.cosmetic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class unbreakable {
    public static void setUnbreakable(Player p)
    {

    ItemStack item = p.getInventory().getItemInMainHand();
    if (item == null)
        return;
    ItemMeta meta = item.getItemMeta();

    if (meta == null)
        return;

    if (meta.isUnbreakable())
        meta.setUnbreakable(false);
    else
        meta.setUnbreakable(true);

    item.setItemMeta(meta);
    sendPlayerMessage(p,"§7Die §eUnzerstörbarkeit §7wurde auf §e" + meta.isUnbreakable() + " §7gesetzt");
    }
}
