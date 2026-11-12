package de.main.serverCoreX.commands;


import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static de.main.serverCoreX.helper.messages.getNoPerm;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.permissionCheck.hasPermission;

public class sizeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("size") && sender instanceof Player p)
        {
            if (!hasPermission(p,"size.use"))
                return true;

            if (args.length != 1) {
                sendPlayerMessage(p,"§7Hey, deine §eEingabe §7ist §eungültig! §7(Nutze /size 1.5)");
                return true;
            }

            try {
                double size = Double.parseDouble(args[0]);
                AttributeInstance scale = p.getAttribute(Attribute.SCALE);

                if (scale != null) {
                    scale.setBaseValue(size);

                    sendPlayerMessage(p,"§7Deine §eGröße §7wurde auf §e" + size + " §7gesetzt!");
                }

            } catch (NumberFormatException e) {
                sendPlayerMessage(p,"§7Hey, deine §eEingabe §7ist §eungültig!");
            }
        }

        return true;
    }
}
