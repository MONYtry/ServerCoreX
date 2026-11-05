package de.main.serverCoreX.commands;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static de.main.serverCoreX.helper.messages.*;

public class RestartCommand implements Listener {

    private boolean isRunning = false;
    private BukkitRunnable restartTask;
    private BossBar bossBar;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        // Fängt den Command ab
        if (!e.getMessage().equalsIgnoreCase("/restart"))
            return;

        Player p = e.getPlayer();

        if (!p.hasPermission("serverrestart.use"))
        {
            sendPlayerMessage(p,getNoPerm());
            return;
        }

        e.setCancelled(true);

        // Falls bereits schon gestoppt werden soll, abbruch
        if (clearTask())
            return;

        isRunning = true;

        bossBar = Bukkit.createBossBar(getPrefix() + "§cServer restartet in 60", BarColor.RED, BarStyle.SEGMENTED_6);

        restartTask = new BukkitRunnable()
        {
            int time = 60;

            @Override
            public void run()
            {
                // Gibt allen Spieler die Online sind eine Bossbar mit Timer
                for (Player p : Bukkit.getOnlinePlayers())
                {
                    if (!bossBar.getPlayers().contains(p))
                    {
                        bossBar.addPlayer(p);
                    }
                }

                // Bossbar updaten
                bossBar.setTitle("Server neustart in " + time);
                bossBar.setProgress((double) time / 60);

                if (time == 60 || time == 30)
                {
                    sendPlayerinfomation(time,Sound.ITEM_GOAT_HORN_SOUND_0);
                }

                // Sekündlich Nachrichten ab 10 Sekunden
                if (time <= 10 && time > 0)
                {
                    sendPlayerinfomation(time,Sound.UI_BUTTON_CLICK);
                }

                // Falls die Zeit 0 oder niedrieger ist.
                if (time <= 0)
                {
                    // Stoppt den Server
                    initiateShutdown();
                    // Bricht den Timer ab
                    cancel();

                    return;
                }
                time--;
            }
        };

        restartTask.runTaskTimer(ServerCoreX.getInstance(), 0, 20);
    }

    private void initiateShutdown()
    {
        bossBar.removeAll();
        isRunning = false;
        Bukkit.shutdown();
    }

    private void sendPlayerinfomation(int time, Sound sound)
    {
        Bukkit.broadcastMessage(getPrefix() +"§7Server wird in §c" + time + "§7 Sekunden §cneugestartet");

        // Gibt allen Spieler die Online sind einen Sound
        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.playSound(p.getLocation(), sound, 1, 1);
        }
    }


    private boolean clearTask()
    {
        // Falls bereits schon gestoppt werden soll, abbruch
        if (isRunning)
        {

            Bukkit.broadcastMessage(getPrefix() + "§cRestart wurde gestoppt!");

            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.sendTitle("§f§lNEUSTART ABGEBROCHEN", "§cKein Server-Neustart");
                p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_DEATH, 1, 1);
            }

            if (restartTask != null)
                restartTask.cancel();

            if (bossBar != null)
                bossBar.removeAll();

            isRunning = false;
            return true;
        }
        return false;
    }
}

