package de.main.serverCoreX.commands.sign;

import de.main.serverCoreX.ServerCoreX;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static de.main.serverCoreX.helper.colorMessage.handleColor;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class signCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("sign"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null)
        {
            sendPlayerMessage(p,"§7Du musst ein Item in der Hand haben um dies Signieren zu können!");
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        NamespacedKey key = new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class),"signed");
        if (meta.getPersistentDataContainer().has(key, PersistentDataType.BYTE) && !p.hasPermission("sign.bypass"))
        {
            sendPlayerMessage(p,"§cItem bereits signiert!");
            return true;
        }

        List<Component> lore = meta.lore();

        if (lore == null)
            lore = new ArrayList<>();

        LocalDate rawLocalDate = LocalDate.now();

        String[] splitedLocalDate = rawLocalDate.toString().split("-");
        String date = splitedLocalDate[2] +"."+ splitedLocalDate[1] + "." + splitedLocalDate[0];

        Component formattedLine = handleColor("§7Signiert am §e" + date + " §7von §b@" + p.getName());

        lore.add(Component.text("§e"));
        lore.add(Component.text("§e"));
        lore.set(lore.size() -1, formattedLine);
        lore.add(Component.text("§e"));
        meta.lore(lore);

        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        sendPlayerMessage(p,"§7Die §eSignatur §7wurde erfolgreich §ehinzugefügt!");
        return true;
    }
}
