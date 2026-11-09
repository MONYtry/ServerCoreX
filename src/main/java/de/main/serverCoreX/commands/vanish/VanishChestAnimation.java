
package de.main.serverCoreX.commands.vanish;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.plugin.java.JavaPlugin;
import static de.main.serverCoreX.commands.vanish.vanishListener.vanishedChests;

public class VanishChestAnimation {

    public static void register(JavaPlugin plugin) {

        // Registrierung von Protocollib
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(
                        plugin,
                        // Legt die priorität vest
                        ListenerPriority.NORMAL,
                        // Packet-Typ
                        PacketType.Play.Server.BLOCK_ACTION) {

            // Wird aufgerufen sobald ein Packet an den Spieler gesendet wird
            @Override
            public void onPacketSending(PacketEvent event)
            {
                // Action erkennen
                int action = event.getPacket().getIntegers().read(0);

                // Action 1 = Chest Animation
                if (action != 1)
                    return;

                // Holt sich die Block Position
                BlockPosition pos = event.getPacket().getBlockPositionModifier().read(0);

                // Erstellt String mit welchem dann abgefragt wird
                String key = event.getPlayer().getWorld().getName() + ":" +
                        pos.getX() + ":" +
                        pos.getY() + ":" +
                        pos.getZ();
                // Falls die Kiste im Vanish offen ist
                if (vanishedChests.contains(key)) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
