package de.main.serverCoreX.commands.itemedit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
            case "lore":
                handleLoreEdit(args,p);
                break;
            default:
                sendPlayerMessage(p,"Nicht gefunden!");
                break;
        }
        return true;
    }

    private void handleLoreEdit(String[] args,Player p)
    {
        switch (args[1].toString().toLowerCase())
        {
            case "add":
                if (args[2] == null)
                {
                    p.sendMessage("Fehler du hast keine neue Lore angegebn!");
                    return;
                }
                if (p.getInventory().getItemInMainHand().isEmpty())
                {
                    sendPlayerMessage(p,"§cDu hast kein Item in der Hand");
                    return;
                }

                ItemStack itemStack = p.getInventory().getItemInMainHand();

                // Holt sich die Eingabe des Nutzers
                String newLine = args[2];

                // Holt sich die bereits gesetze Lore
                List<String> lore = itemStack.getLore();

                // Falls keine Lore existiert, erstellt eine
                if (lore == null)
                {
                    lore = new ArrayList<>();
                }
                lore.add(newLine);

                // Fügt die Lore hinzu
                itemStack.setLore(lore);
                p.sendMessage("Lore wurde hinzugefügt!");
        }
    }
}
