
package de.main.serverCoreX.economy;

import de.main.serverCoreX.ServerCoreX;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;

public class VaultHook implements Economy {

    private final EconomyManager manager;

    public VaultHook(EconomyManager manager) {
        this.manager = manager;
    }

    public static void register(ServerCoreX plugin) {
        plugin.getServer().getServicesManager().register(
                Economy.class,
                new VaultHook(plugin.economyManager),
                plugin,
                ServicePriority.High
        );
    }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getName() { return "ServerCoreX"; }

    @Override
    public boolean hasBankSupport() { return false; }

    @Override
    public int fractionalDigits() { return 2; }

    @Override
    public String format(double amount) {
        return amount + " Coins";
    }

    @Override
    public String currencyNamePlural() { return "Coins"; }

    @Override
    public String currencyNameSingular() { return "Coin"; }

    @Override
    public boolean hasAccount(OfflinePlayer player) { return true; }

    @Override
    public double getBalance(OfflinePlayer player) {
        return manager.getBalance(player.getUniqueId());
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        // Falls der einzuzahlende Betrag weniger als 0 ist
        if (amount <= 0)
            return failure(amount, player);

        manager.setBalance(player.getUniqueId(), getBalance(player) + amount);
        return success(amount, player);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount)
    {
        double balance = getBalance(player);

        // Falls Geld weniger als 0 ist
        // oder der auszuzahlender Betrag zu hoch ist
        if (amount <= 0 || balance < amount)
            return failure(amount, player);

        manager.setBalance(player.getUniqueId(), balance - amount);
        return success(amount, player);
    }

    private EconomyResponse success(double amount, OfflinePlayer player) {
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, null);
    }

    private EconomyResponse failure(double amount, OfflinePlayer player) {
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Nicht genügend Geld!");
    }

    // Nicht benötigte Vault-Methoden
    @Override public boolean hasAccount(String p)
    {
        return manager.hasAccount(Bukkit.getOfflinePlayer(p).getUniqueId());
    }
    @Override public boolean hasAccount(OfflinePlayer p, String w) { return hasAccount(p); }
    @Override public boolean hasAccount(String p, String w) { return false; }
    @Override public double getBalance(String p) { return 0; }
    @Override public double getBalance(OfflinePlayer p, String w) { return getBalance(p); }
    @Override public double getBalance(String p, String w) { return 0; }
    @Override public boolean has(String p, double a) { return false; }
    @Override public boolean has(OfflinePlayer p, String w, double a) { return has(p, a); }
    @Override public boolean has(String p, String w, double a) { return false; }
    @Override public EconomyResponse withdrawPlayer(String p, double a) { return null; }
    @Override public EconomyResponse withdrawPlayer(OfflinePlayer p, String w, double a) { return withdrawPlayer(p, a); }
    @Override public EconomyResponse withdrawPlayer(String p, String w, double a) { return null; }
    @Override public EconomyResponse depositPlayer(String p, double a) { return null; }
    @Override public EconomyResponse depositPlayer(OfflinePlayer p, String w, double a) { return depositPlayer(p, a); }
    @Override public EconomyResponse depositPlayer(String p, String w, double a) { return null; }
    @Override public EconomyResponse createBank(String n, String p) { return null; }
    @Override public EconomyResponse createBank(String n, OfflinePlayer p) { return null; }
    @Override public EconomyResponse deleteBank(String n) { return null; }
    @Override public EconomyResponse bankBalance(String n) { return null; }
    @Override public EconomyResponse bankHas(String n, double a) { return null; }
    @Override public EconomyResponse bankWithdraw(String n, double a) { return null; }
    @Override public EconomyResponse bankDeposit(String n, double a) { return null; }
    @Override public EconomyResponse isBankOwner(String n, String p) { return null; }
    @Override public EconomyResponse isBankOwner(String n, OfflinePlayer p) { return null; }
    @Override public EconomyResponse isBankMember(String n, String p) { return null; }
    @Override public EconomyResponse isBankMember(String n, OfflinePlayer p) { return null; }
    @Override public java.util.List<String> getBanks() { return java.util.Collections.emptyList(); }
    @Override public boolean createPlayerAccount(OfflinePlayer p) { return true; }
    @Override public boolean createPlayerAccount(String p) { return false; }
    @Override public boolean createPlayerAccount(OfflinePlayer p, String w) { return true; }
    @Override public boolean createPlayerAccount(String p, String w) { return false; }
}
