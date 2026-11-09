package de.main.serverCoreX.economy;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EconomyManager {

    private static File balancesFile;
    private static YamlConfiguration balances;

    public EconomyManager(ServerCoreX plugin) {
        balancesFile = new File(plugin.getDataFolder(), "balances.yml");

        if (!balancesFile.exists()) {
            try {
                balancesFile.getParentFile().mkdirs();
                balancesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        balances = YamlConfiguration.loadConfiguration(balancesFile);
    }

    private static String path(UUID uuid) {
        return "balances." + uuid;
    }

    public static void createAccount(UUID uuid) {
        if (hasAccount(uuid)) return;

        balances.set(path(uuid) + ".balance", 1000.0);
        save();
    }

    public static boolean hasAccount(UUID uuid) {
        return balances.contains(path(uuid));
    }

    public static double getBalance(UUID uuid) {
        return balances.getDouble(path(uuid) + ".balance");
    }

    public static double getOnlineBalance(UUID uuid) {
        return balances.getDouble(path(uuid) + ".onlinebalance");
    }

    public static void setBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid) || amount < 0) return;

        balances.set(path(uuid) + ".balance", amount);
        save();
    }

    public static void setOnlineBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid))
            return;
        if (amount < 0)
            return;

        balances.set(path(uuid) + ".onlinebalance", amount);
        save();
    }

    public static boolean removeFromBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid) || amount <= 0 || getBalance(uuid) < amount)
            return false;

        setBalance(uuid, getBalance(uuid) - amount);
        return true;
    }

    public static boolean removeFromOnlineBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid))
            return false;
        if (amount <= 0)
            return false;
        if (getOnlineBalance(uuid) < amount)
            return false;

        setOnlineBalance(uuid, getOnlineBalance(uuid) - amount);
        return true;
    }

    public static void addBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid) || amount <= 0) return;

        setBalance(uuid, getBalance(uuid) + amount);
    }

    public static void addOnlineBalance(UUID uuid, double amount) {
        if (!hasAccount(uuid) || amount <= 0) return;

        setOnlineBalance(uuid, getOnlineBalance(uuid) + amount);
    }

    public static void save() {
        if (balances == null) return;

        try {
            balances.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

