
package de.main.serverCoreX.commands.itemedit.lore;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static de.main.serverCoreX.helper.colorMessage.handleColor;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class lore {

    public static boolean loreAdd(String newLine, ItemStack item)
    {
        if (item == null || item.getType().isAir())
            return false;

        ItemMeta meta = item.getItemMeta();

        List<Component> lore = meta.lore();

        if (lore == null)
            lore = new ArrayList<>();

        Component formattedLine = handleColor(newLine);

        lore.add(formattedLine);

        meta.lore(lore);
        item.setItemMeta(meta);

        return true;
    }


    public static boolean loreSet(String newLine, ItemStack item)
    {
        if (item == null || item.getType().isAir())
            return false;

        int index;
        // Splitted nach einer Leertaste, es dürfen maximal 2 Teile gemacht werden
        String[] rawIndex = newLine.split(" ",2);

        try {
            // Replaced das line mit nichts, -1 um ein angenehmeres Bedienungs-Gefühl zu erhalten
            index = Integer.parseInt(rawIndex[0].replace("line", "")) - 1;
        } catch (NumberFormatException e) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        List<Component> lore = meta.lore();

        if (lore == null || index < 0 || index >= lore.size())
            lore = new ArrayList<>();

        Component formattedLine = handleColor(newLine);
        lore.set(index, formattedLine);

        meta.lore(lore);
        return true;
    }

    public static void loreClear(Player p)
    {
        ItemStack itemStack = p.getInventory().getItemInMainHand();

        if (itemStack == null)
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);
        sendPlayerMessage(p, "§aLore wurde erfolgreich §cgelöscht!");
    }

}
