
package de.main.serverCoreX.kit;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.entity.Player;
import java.util.UUID;

import static de.main.serverCoreX.helper.ItemCreator.autoGenItemsWithConfigGUI;
import static de.main.serverCoreX.helper.ItemCreator.giveItemsWithConfig;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;
import static de.main.serverCoreX.kit.kitManager.addToCollectedKit;
import static de.main.serverCoreX.kit.kitManager.getAlreadyCollectedKit;

public class kit {

    public static void giveKit(Player p) {

        UUID uuid = p.getUniqueId();

        if (getAlreadyCollectedKit(uuid)) {
            sendPlayerMessage(p, "§7Du hast bereits dein §eStarter-Kit §7abgeholt!");
            return;
        }

        giveItemsWithConfig(p, ServerCoreX.getInstance().startKitConfig);

        addToCollectedKit(uuid);

        sendPlayerMessage(p, "§7Du hast das §eStarter-Kit §7erfolgreich abgeholt!");

    }
}
