package de.main.serverCoreX.economy.bank;

import de.main.serverCoreX.ServerCoreX;
import de.main.serverCoreX.economy.EconomyManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import static de.main.serverCoreX.helper.messages.getPrefix;
import static de.main.serverCoreX.helper.messages.sendPlayerMessage;

public class bankListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        String titel = e.getView().getTitle();
        if (!titel.equalsIgnoreCase(getPrefix() + "§eBank"))
            return;

        e.setCancelled(true);

        if (e.getCurrentItem() == null)
            return;

        ItemStack item = e.getCurrentItem();
        if (!item.hasItemMeta())
            return;

        ItemMeta itemMeta = item.getItemMeta();

        // Holt sich die Action
        String action = itemMeta.getPersistentDataContainer().get(new NamespacedKey(ServerCoreX.getInstance(), "action"), PersistentDataType.STRING);
        if (action == null)
            return;

        Player p = (Player) e.getWhoClicked();
        switch (action.toLowerCase())
        {
            case "auszahlen":
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,1,1);
                p.closeInventory();
                openAmountInput(p,action);
                break;

            case "einzahlen":
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL,1,1);
                openAmountInput(p,action);
                break;

            default:
                p.closeInventory();
                sendPlayerMessage(p,"§7Das §eBank-Menü §7wurde geschlossen!");
                break;
        }
    }

    private void openAmountInput(Player p, String action)
    {
        String color = "§7";
        if (action.equalsIgnoreCase("einzahlen"))
            color = "§a";
        else
            color = "§c";

        // Erstellt eine AnvilGUI mit einem Onclick Event
        new AnvilGUI.Builder().onClick((slot, stateSnapshot) -> {

            if (slot != AnvilGUI.Slot.OUTPUT) {
                return Collections.emptyList();
            }

            // Holt sich die Eingabe
            String text = stateSnapshot.getText();

            double amount;
            try
            {
                amount = Double.parseDouble(text);
            }
            catch (NumberFormatException ex)
            {
                // Falls es keine Zahl ist
                return List.of(AnvilGUI.ResponseAction.run(() -> sendPlayerMessage(p, "§cBitte gib eine gültige Zahl ein!")));
            }
            // Verhindert Zahlen die kleiner als 0 sind
            if (amount <= 0)
            {
                return List.of(AnvilGUI.ResponseAction.run(() -> sendPlayerMessage(p, "§cDer Betrag muss größer als 0 sein!")));
            }

            // Verarbeitet die Action (Ein/-Auszahlen)
            handleInput(action,p,amount);

            return List.of(AnvilGUI.ResponseAction.run(() -> {p.closeInventory();}), AnvilGUI.ResponseAction.close());

        }).text("Betrag eingeben").title("§eBank §8» §7" + color + action.toUpperCase()).plugin(ServerCoreX.getInstance()).open(p);
    }

    private List<AnvilGUI.@NotNull ResponseAction> handleInput(String action, Player p, double amount)
    {
       if (action.equalsIgnoreCase("auszahlen"))
       {
           // Falls nicht genug Guthaben vorhanden ist, abbruch
           if (!EconomyManager.removeFromOnlineBalance(p.getUniqueId(), amount))
            return List.of(AnvilGUI.ResponseAction.run(() ->
            {
                handleNotEnoughBalance(p);
            }));
           EconomyManager.addBalance(p.getUniqueId(),amount);
           sendPlayerMessage(p, "§7Du hast erfolgreich §e" + amount + "$ §7abgehoben");
       }

       else if (action.equalsIgnoreCase("einzahlen"))
       {
            // Falls nicht genug Guthaben vorhanden ist, abbruch
            if (!EconomyManager.removeFromBalance(p.getUniqueId(), amount))
                return List.of(AnvilGUI.ResponseAction.run(() ->
                {
                    handleNotEnoughBalance(p);
                }));

            EconomyManager.addOnlineBalance(p.getUniqueId(),amount);
            sendPlayerMessage(p, "§7Du hast erfolgreich §e" + amount + "$ §7eingezahlt");
       }

        return null;
    }

    private void handleNotEnoughBalance(Player p)
    {
        sendPlayerMessage(p, "§7Dein §eGuthaben §7reicht §enicht §7aus!");
        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
    }
}
