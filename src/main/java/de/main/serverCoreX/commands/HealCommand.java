package de.main.serverCoreX.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.HashSet;
import java.util.UUID;

import static de.main.serverCoreX.helper.messages.getNoPerm;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.timer.delay;

public class HealCommand implements Listener {

    private HashSet<UUID> playerCooldownList = new HashSet<>();

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        if (!e.getMessage().equalsIgnoreCase("/heal"))
            return;

        e.setCancelled(true);

        // Permission Check
        if (!e.getPlayer().hasPermission("heal.use")) {
            sendPlayerMessage(e.getPlayer(),getNoPerm());
            return;
        }

        Player p = e.getPlayer();
        if (playerCooldownList.contains(p.getUniqueId()))
        {
            sendPlayerMessage(p,"Hey du musst noch warten!");
            return;
        }

        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
        p.setSaturation(20f);
        p.setExhaustion(0f);
        sendPlayerMessage(p,"§7Du wurdest §egeheilt!");
        playerCooldownList.add(p.getUniqueId());

        // Nutzung meiner Hilfs-Funktion
        delay(() -> {
                    playerCooldownList.remove(p.getUniqueId());
                    sendPlayerMessage(p, "§7Du kannst nun wieder §e/heal §7nutzen");
                }, 40L);
    }
}