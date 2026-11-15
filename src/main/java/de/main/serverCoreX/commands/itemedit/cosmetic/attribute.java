package de.main.serverCoreX.commands.itemedit.cosmetic;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class attribute {

    public static void setAttribute(Player p, String attribute, String input)
    {
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null)
            return;
        ItemMeta meta = item.getItemMeta();

        if (meta == null)
            return;

        double amount = 0;
        try {
            amount = Double.parseDouble(input);
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        if (meta.hasAttributeModifiers())
            meta.setAttributeModifiers(null);

        try {
        AttributeModifier modifier = new AttributeModifier(
                new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class), attribute),
                amount,
                AttributeModifier.Operation.ADD_NUMBER,
                EquipmentSlotGroup.OFFHAND
        );

        Attribute target = Registry.ATTRIBUTE.get(NamespacedKey.minecraft(attribute.toLowerCase()));
        if (target == null)
        {
            sendPlayerMessage(p,"§7Der angebene §eAttribute §7konnte nicht §egefunden §7werden!");
            return;
        }
        meta.addAttributeModifier(target, modifier);
        } catch (Exception e) {
            sendPlayerMessage(p,e.toString());
        }

        item.setItemMeta(meta);
        sendPlayerMessage(p, "§7Der Attribute §e" + attribute.toUpperCase() + " §7wurde erfolgreich §ehinzugefügt!");
    }
}
