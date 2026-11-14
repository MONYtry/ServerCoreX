package de.main.serverCoreX.commands.itemedit;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class enchantment {
    public static void addEnchantment(String inputEnchantment,String inputLevel, Player p)
    {
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null)
            return;
        int level = 1;
        try {
            level = Integer.parseInt(inputLevel);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Enchantment enchantment = Enchantment.getByName(inputEnchantment);
        if (enchantment == null)
        {
            sendPlayerMessage(p,"§cVerzauberung konnte nicht gefunden werden!");
            return;
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(enchantment,level,true);
        item.setItemMeta(itemMeta);

        sendPlayerMessage(p,"§cEnchantment wurde hinzugefügt");
    }
}
