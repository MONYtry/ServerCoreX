package de.main.serverCoreX.commands.itemedit.displayname;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.main.serverCoreX.helper.colorMessage.handleColor;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class displayname {

    public static void setDisplayname(Player p, String name)
    {
        ItemStack itemStack = p.getInventory().getItemInMainHand();
        if (itemStack.isEmpty())
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        Component test = handleColor(name);

        itemMeta.displayName(test);

        itemStack.setItemMeta(itemMeta);
        sendPlayerMessage(p, "§7Der §eDisplayname §7wurde geändert!");
    }

    public static void clearDisplayname(Player p)
    {
        ItemStack itemStack = p.getInventory().getItemInMainHand();
        if (itemStack.isEmpty())
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(null);
        itemStack.setItemMeta(itemMeta);
        sendPlayerMessage(p, "§7Der §eDisplayname §7wurde gelöscht!");
    }
}
