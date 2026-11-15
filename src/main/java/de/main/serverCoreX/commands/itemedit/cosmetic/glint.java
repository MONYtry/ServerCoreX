package de.main.serverCoreX.commands.itemedit.cosmetic;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class glint {

    public static void setGlint(Player p)
    {

        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null)
            return;
        ItemMeta meta = item.getItemMeta();

        if (meta == null)
            return;

        if (meta.hasEnchantmentGlintOverride())
            meta.setEnchantmentGlintOverride(false);
        else
            meta.setEnchantmentGlintOverride(true);



        item.setItemMeta(meta);
        sendPlayerMessage(p,"§7Die §eLeuchten §7wurde auf §e" + meta.hasEnchantmentGlintOverride() + " §7gesetzt");
    }

}
