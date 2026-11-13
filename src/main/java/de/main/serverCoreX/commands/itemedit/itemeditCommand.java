package de.main.serverCoreX.commands.itemedit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.commands.itemedit.displayname.displayname.clearDisplayname;
import static de.main.serverCoreX.commands.itemedit.displayname.displayname.setDisplayname;
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
            case "clear":
                handleClear(args,p);
                break;
            default:
                sendPlayerMessage(p,"Nicht gefunden!");
                break;
        }
        return true;
    }


    private void handleAdd(String[] args, Player p)
    {
        if (args.length < 3) {
            sendPlayerMessage(p, "§cNutze: /itemedit add lore <Text>");
            return;
        }
        String text = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);

        switch (args[1].toLowerCase())
        {
            case "lore":
                if (loreAdd(text, p.getInventory().getItemInMainHand()))
                    sendPlayerMessage(p, "§aErfolgreich Lore hinzugefügt!");
                else
                    sendPlayerMessage(p, "§cDu musst ein Item in der Hand halten!");
                break;

            default:
                sendPlayerMessage(p, "§cUnbekannter Typ!");
                break;
        }
    }

    private void handleSet(String[] args, Player p)
    {
        if (args.length < 1) {
            sendPlayerMessage(p, "§cNutze: /itemedit set <attribute>");
            return;
        }
        String text = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);

        switch (args[1].toLowerCase())
        {
            case "lore":
                if (args.length < 4) {
                    sendPlayerMessage(p, "§cNutze: /itemedit set <attribute> <Text> <Index>");
                    return;
                }

                if (loreSet(text, p.getInventory().getItemInMainHand()))
                    sendPlayerMessage(p, "§aLore erfolgreich geändert!");
                else
                    sendPlayerMessage(p, "§cLore konnte nicht geändert werden!");
                break;

            case "displayname":
                setDisplayname(p,text);
                break;
        }
    }

    private void handleClear(String[] args, Player p)
    {
        if (args.length < 1) {
            sendPlayerMessage(p, "§cNutze: /itemedit clear <attribute>");
            return;
        }

        switch (args[1].toLowerCase())
        {
            case "lore":
                loreClear(p);
                break;
            case "displayname":
                clearDisplayname(p);
                break;
        }
    }

}
