package de.main.serverCoreX.joinLeave;

import de.main.serverCoreX.scoreboard.ScoardboardManager;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.ScoreboardManager;

import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.kit.kit.giveKit;

public class joinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore())
            firstJoinManager.addFirstJoin(p);

        e.setJoinMessage(getPrefix() + "§e[+] " + p.getName());

        Location playerLocation = p.getLocation();
        p.playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_SHOOT,1,1);
        p.playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE,1,1);
        p.playSound(playerLocation, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR,1,1);

        p.sendTitle("§f§lWillkommen auf", "§6BLOCKHIVE.NET");
        p.performCommand("spawn");

        ScoardboardManager.createScoreboard(p);
        giveKit(p);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        e.setQuitMessage(getPrefix() + "§e[-] " + p.getName());
    }
}
