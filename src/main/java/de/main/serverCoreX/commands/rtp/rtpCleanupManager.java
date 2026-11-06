package de.main.serverCoreX.commands.rtp;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.*;
import static de.main.serverCoreX.helper.timer.delay;

public class rtpCleanupManager {

    private static final Map<Location, Material> originalBlocks = new HashMap<>();
    private static final List<Location> rtpLocations = new ArrayList<>();

    private static final int RADIUS = 5;

    public static void registerRtpSpot(Location center) {

        // Ursprüngliche Blöcke speichern
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {

                Block block = center.clone().add(x, -1, z).getBlock();

                originalBlocks.put(block.getLocation(), block.getType());
            }
        }
        // Fügt es der Liste hinzu
        rtpLocations.add(center.clone());

        // Nach 1 Minute löschen
        delay(() -> {
            cleanup(center);
        }, 20L * 30);
    }

    private static void cleanup(Location center) {
        // Holt sich die Welt und check Null-Reference
        World world = center.getWorld();
        if (world == null) return;

        // Prüfen, ob noch ein Spieler in der Nähe ist
        for (Player player : world.getPlayers()) {

            // Falls ein Spieler im Umkreis von x * x Blöcken ist, wieder warten
            if (player.getLocation().distanceSquared(center) <= RADIUS * RADIUS) {
                // Noch jemand da -> später erneut prüfen
                delay(() -> {
                    cleanup(center);
                }, 20L * 30);
                return;
            }
        }

        // 3x3 Boden entfernen
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                center.clone().add(x, -1, z).getBlock().setType(Material.AIR);
            }
        }

        // Entities im Umkreis entfernen
        for (Entity entity : world.getNearbyEntities(center, 5, 5, 5)) {
            if (!(entity instanceof Player)) {
                entity.remove();
            }
        }
        // Entfernt wieder aus der Liste
        rtpLocations.remove(center);
    }
}