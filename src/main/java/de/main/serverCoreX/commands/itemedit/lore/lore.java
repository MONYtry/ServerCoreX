package de.main.serverCoreX.commands.itemedit.lore;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class lore {

    public static boolean loreAdd(String newLine,ItemStack item)
    {
        if (item == null)
            return false;

        // Holt sich die bereits gesetze Lore
        List<String> lore = item.getLore();

        // Falls keine Lore existiert, erstellt eine
        if (lore == null)
        {
            lore = new ArrayList<>();
        }
        lore.add(newLine);

        // Fügt die Lore hinzu
        item.setLore(lore);
        return true;
    }

    public static boolean loreSet(String newLine,String rawIndex,ItemStack item)
    {
        if (item == null)
            return false;
        int index = 0;
        try {
            index = Integer.parseInt(rawIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Holt sich die bereits gesetze Lore
        List<String> lore = item.getLore();

        // Falls keine Lore existiert, erstellt eine
        if (lore == null)
        {
            lore = new ArrayList<>();
        }
        lore.set(index,newLine);

        // Fügt die Lore hinzu
        item.setLore(lore);
        return true;
    }
}