package de.main.serverCoreX.commands.itemedit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.main.serverCoreX.commands.itemedit.lore.lore.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class itemeditCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!command.getName().equalsIgnoreCase("itemedit"))
            return true;

        if (!(sender instanceof Player p))
            return true;
        if (args.length == 0)
        {
            sendPlayerMessage(p,"§cNutze /itemedit (enchant,lore)");
            return true;
        }

        switch (args[0].toLowerCase())
        {
            case "set":
                handleSet(args,p);
                break;
            case "add":
                handleAdd(args,p);
                break;
            default:
                sendPlayerMessage(p,"Nicht gefunden!");
                break;
        }
        return true;
    }

    private void handleAdd(String[] args,Player p)
    {
        switch (args[1].toLowerCase())
        {
            case "lore":
                if (loreAdd(args[2],p.getInventory().getItemInMainHand()))
                    sendPlayerMessage(p,"Erfolgreich lore hinzugefügt");
                break;

        }
    }

    private void handleSet(String[] args,Player p)
    {
        switch (args[1].toLowerCase())
        {
            case "lore":
                loreSet(args[2],args[3],p.getInventory().getItemInMainHand());
                break;
        }
    }
}
