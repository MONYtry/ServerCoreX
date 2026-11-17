package de.main.serverCoreX.commands.customDB;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.helper.ItemCreator.createCustomModelInventory;
import static de.main.serverCoreX.helper.messages.getPrefix;


public class customItemDB implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("customItem"))
            return true;

        if (!(sender instanceof Player p))
            return true;

        Inventory inv = Bukkit.createInventory(null, 36,getPrefix() + "§cCustom-Items");
        createCustomModelInventory(inv, ServerCoreX.getInstance().getConfig());
        p.openInventory(inv);
        return true;
    }


}
