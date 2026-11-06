package de.main.serverCoreX;

import de.main.serverCoreX.commands.*;
import de.main.serverCoreX.commands.teleport.AcceptCommand;
import de.main.serverCoreX.commands.teleport.tpCommand;
import de.main.serverCoreX.commands.teleport.tpaCommand;
import de.main.serverCoreX.commands.teleport.tphereCommand;
import de.main.serverCoreX.joinLeave.joinListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import static de.main.serverCoreX.BroadcastManager.startMessages;

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
        Bukkit.getPluginManager().registerEvents(new joinListener(),this);

        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("tpa").setExecutor(new tpaCommand());
        getCommand("tpyes").setExecutor(new AcceptCommand());
        getCommand("tp").setExecutor(new tpCommand());
        getCommand("tphere").setExecutor(new tphereCommand());
        getCommand("discord").setExecutor(new discord());

        saveResource("messages.yml",false);

        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"messages.yml"));
        getLogger().info("Erfolgreich geladen!");

        startMessages();
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
