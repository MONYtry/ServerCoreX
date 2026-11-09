
package de.main.serverCoreX.economy;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EconomyManager {

    private final File balancesFile;
    private final YamlConfiguration balances;

    public EconomyManager(ServerCoreX plugin)
    {
        // Holt Datei von dem standard Plugin-Path und mit dem Namen "balances.yml"
        balancesFile = new File(plugin.getDataFolder(), "balances.yml");

        // Falls Datei nicht exsitiert, erstellt Datei
        if (!balancesFile.exists())
        {
            try
            {
                balancesFile.getParentFile().mkdirs();
                balancesFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        // Setzt die Config auf die eben erstellte Datei
        balances = YamlConfiguration.loadConfiguration(balancesFile);
    }

    public void createAccount(UUID uuid)
    {
        if (hasAccount(uuid))
            return;

        balances.set("balances." + uuid, 100.0);
        save();
    }

    public boolean hasAccount(UUID uuid)
    {
        return balances.contains("balances." + uuid);
    }

    public double getBalance(UUID uuid) {
        return balances.getDouble("balances." + uuid, 100.0);
    }

    public void setBalance(UUID uuid, double amount)
    {
        if (!hasAccount(uuid))
            return;

        balances.set("balances." + uuid, amount);
        save();
    }

    public void removeFromBalance(UUID uuid, double amount)
    {
        if (!hasAccount(uuid))
            return;

        if (getBalance(uuid) - amount <= 0)
            return;

        balances.set("balances." + uuid, getBalance(uuid) - amount);
        save();
    }

    public void addBalance(UUID uuid, double amount)
    {
        if (!hasAccount(uuid))
            return;

        balances.set("balances." + uuid, getBalance(uuid) + amount);
        save();
    }

    public void save() {
        try
        {
            balances.save(balancesFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
