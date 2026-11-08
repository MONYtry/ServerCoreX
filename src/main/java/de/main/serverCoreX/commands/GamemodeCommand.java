package de.main.serverCoreX.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

import static de.main.serverCoreX.helper.messages.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (!command.getName().equalsIgnoreCase("gm"))
            return true;

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Diesen Befehl darf nur ein Spieler ausführen!");
            return true;
        }

        if (!p.hasPermission("gamemode.use")) {
            sendPlayerMessage(p, getCommandNotExist());
            return true;
        }

        if (args.length < 1) {
            sendPlayerMessage(p, getCommandNotExist());
            return true;
        }

        // Zielspieler bestimmen
        Player target = p;

        if (args.length >= 2) {
            if (!p.hasPermission("gamemode.others")) {
                sendPlayerMessage(p, getCommandNotExist());
                return true;
            }

            target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                FileConfiguration file = getMessages();

                sendPlayerMessage(p,file.getString(getPath() + "player_not_online"));
                return true;
            }
        }

        // Gamemode setzen
        switch (args[0]) {

            case "1":
                target.setGameMode(GameMode.CREATIVE);
                sendPlayerMessage(target,"§7Du bist erfolgreich im §eKreativmodus!");
                if (!target.equals(p)) p.sendMessage("§aDu hast " + target.getName() + " in den Kreativmodus gesetzt!");
                break;

            case "0":
                target.setGameMode(GameMode.SURVIVAL);
                sendPlayerMessage(target,"§7Du bist erfolgreich im §eÜberlebensmodus!");
                if (!target.equals(p)) p.sendMessage("§aDu hast " + target.getName() + " in den Überlebensmodus gesetzt!");
                break;

            case "2":
                target.setGameMode(GameMode.ADVENTURE);
                sendPlayerMessage(target,"§7Du bist erfolgreich im §eAbenteuermodus!");
                if (!target.equals(p)) p.sendMessage("§aDu hast " + target.getName() + " in den §eAbenteuermodus gesetzt!");
                break;

            case "3":
                target.setGameMode(GameMode.SPECTATOR);
                sendPlayerMessage(target,"§7Du bist erfolgreich im §eBeobachtungsmodus!");
                if (!target.equals(p)) p.sendMessage("§aDu hast " + target.getName() + " in den Beobachtungsmodus gesetzt!");
                break;

            default:
                sendPlayerMessage(p,"§cBitte nutze /gm (0,1,2,3)");
                break;
        }

        return true;
    }
}
