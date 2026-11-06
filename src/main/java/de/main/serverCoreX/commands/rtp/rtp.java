package de.main.serverCoreX.commands.rtp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static de.main.serverCoreX.commands.rtp.rtpManager.*;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.helper.timer.delay;
import static de.main.serverCoreX.helper.timer.rtpScheduler;

public class rtp {
    public static List<Material> unsafeBlocks = new ArrayList<>();
    static {
        unsafeBlocks.add(Material.WATER);
        unsafeBlocks.add(Material.LAVA);
        unsafeBlocks.add(Material.AIR);
    }

    public static void handleRTP(Player p)
    {
        UUID uuid = p.getUniqueId();

        // BEREITS IN QEUE
        if (getIsInQeue(uuid))
        {
            Bukkit.getScheduler().cancelTask(rtpTask.get(uuid));
            rtpTask.remove(uuid);

            removeOfQeue(uuid);
            p.sendActionBar("§cAbgebrochen!");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_FALL,1,1);
            return;
        }

        // NEU IN QEUE
        addInQeue(uuid);
        sendPlayerMessage(p,"§7Du wurdest der §eWarteschlange §7hinzugefügt!");

        int taskID = rtpScheduler(() -> {
            // Falls Spieler bereits verlassen hat
            if (!getIsInQeue(uuid))
            {
                Bukkit.getScheduler().cancelTask(rtpTask.get(uuid));
                rtpTask.remove(uuid);
                return;
            }

            // Erstellt Kopie der Variable
            int countdown = getTime(uuid);

            if (countdown <= 0) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 4,1,true,false));

                // Entfernt User aus RAM
                rtpManager.removeOfQeue(uuid);
                // Bricht die Task ab
                Bukkit.getScheduler().cancelTask(rtpTask.get(uuid));
                // Entfernt User aus der HashMap
                rtpTask.remove(uuid);

                p.sendTitle("§6§lRTP","§fDu wurdest teleportiert!");

                teleportPlayer(p);
                p.playSound(p.getLocation(),Sound.ENTITY_TNT_PRIMED,1,1);
                return;
            }

            // Setzt es zurück in die HashMap und entfernt eine Sekunde
            randomteleportQeues.put(uuid, countdown - 1);

            int watingLine = getPosition(uuid) -1;

            // Falls man nicht der erste in der Warteschlange ist
            if (watingLine != 0)
                randomteleportQeues.put(uuid, countdown + 2);

            // Falls man der erste ist und die Wartedauer zu hoch ist
            if (watingLine == 0 && countdown > 20)
            {
                randomteleportQeues.put(uuid, 20);

                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1,1);
                p.sendTitle("§6§lRTP", "§fDu wirst als Nächstes teleportiert!", 10, 40, 10);
            }

            p.sendActionBar("§fWarte noch §b§l" + getTime(uuid) + "§r§f Sekunden | §b§l" + watingLine + "§r§f Warte-Postition");
        },20);
        rtpTask.put(uuid,taskID);
    }



    private static void teleportPlayer(Player p)
    {
        Random random = new Random();
        double randomX = random.nextInt(2000) - 1000;
        double randomZ = random.nextInt(2000) - 1000;

        World world = p.getWorld();

        Location rawLoc = new Location(world,randomX,0,randomZ);
        // Nutz die eingebaute Funktion von Paper
        // Es wird Asyncron ein Chunk an der gewählten Location geladen
        // Sobald dieser fertig geladen ist wird erst weiter gemacht!
        world.getChunkAtAsync(rawLoc).thenAccept(chunk ->
        {
            int highestBlockYAt = world.getHighestBlockYAt(rawLoc);
            Location finalLoc = new Location(p.getWorld(),randomX +0.5,highestBlockYAt + 1,randomZ +0.5);
            Location boatLoc = new Location(world,randomX + 3, highestBlockYAt + 1, randomZ +0.5);

            // Falls Block nicht sicher ist
            if (unsafeBlocks.contains(finalLoc.getBlock().getType()))
            {
                p.removePotionEffect(PotionEffectType.BLINDNESS);
                for (int x = -1; x <= 1; x++)
                {
                    for (int z = -1; z <= 1; z++)
                    {
                        finalLoc.clone().add(x,-1,z).getBlock().setType(Material.STONE);
                    }
                }
                // Spawnt Boat
                world.spawnEntity(boatLoc, EntityType.BIRCH_BOAT);
                sendPlayerMessage(p,"§cKein sicherer Spot gefunden!");
            }

            p.teleport(finalLoc);
            sendPlayerMessage(p,"§7Du wurdest §eteleportiert");
            p.sendMessage("Godmode wurde aktiviert!");
            p.setInvulnerable(true);

            delay(() -> {
                p.setInvulnerable(false);
                p.sendMessage("Godmode deaktiviert!");
            },20L * 30);

            p.removePotionEffect(PotionEffectType.BLINDNESS);
        });



    }
}
