package de.main.serverCoreX;

import de.main.serverCoreX.commands.FlyCommand;
import de.main.serverCoreX.commands.GamemodeCommand;
import de.main.serverCoreX.commands.HealCommand;
import de.main.serverCoreX.commands.RestartCommand;
import de.main.serverCoreX.commands.teleport.AcceptCommand;
import de.main.serverCoreX.commands.teleport.SendCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ServerCoreX extends JavaPlugin {

    public static ServerCoreX instance;

    public FileConfiguration messages;

    @Override
    public void onEnable()
    {
        instance = this;

        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new RestartCommand(),this);
        Bukkit.getPluginManager().registerEvents(new HealCommand(),this);

        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("tpa").setExecutor(new SendCommand());
        getCommand("tpyes").setExecutor(new AcceptCommand());

        saveResource("messages.yml",false);

        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"messages.yml"));
        getLogger().info("Erfolgreich geladen!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Wird deaktiviert!");
    }


    public static ServerCoreX getInstance()
    {
        return instance;
    }
}
