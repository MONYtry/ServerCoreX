    package de.main.serverCoreX;

    import de.main.serverCoreX.commands.*;
    import de.main.serverCoreX.commands.rtp.rtpCommand;
    import de.main.serverCoreX.commands.rtp.rtpListener;
    import de.main.serverCoreX.commands.rtp.spawnProtection;
    import de.main.serverCoreX.commands.spawn.delSpawn;
    import de.main.serverCoreX.commands.spawn.setSpawn;
    import de.main.serverCoreX.commands.spawn.spawn;
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
    import java.io.IOException;
    import static de.main.serverCoreX.BroadcastManager.startMessages;
    import static de.main.serverCoreX.scoreboard.ScoardboardManager.update;

    public final class ServerCoreX extends JavaPlugin {

        public static ServerCoreX instance;
        public FileConfiguration messages;
        public FileConfiguration locations;
        public FileConfiguration gui;
        public File locationsFile;
        public File messagesFile;
        public File guiFile;

        public File starterKitConfigFile;
        public FileConfiguration startKitConfig;

        public File kitPlayerDataFile;
        public FileConfiguration kitPlayerData;


        @Override
        public void onEnable()
        {
            instance = this;

            saveDefaultConfig();
            Bukkit.getPluginManager().registerEvents(new RestartCommand(),this);
            Bukkit.getPluginManager().registerEvents(new HealCommand(),this);
            Bukkit.getPluginManager().registerEvents(new joinListener(),this);
            Bukkit.getPluginManager().registerEvents(new rtpListener(),this);
            Bukkit.getPluginManager().registerEvents(new spawnProtection(),this);

            getCommand("fly").setExecutor(new FlyCommand());
            getCommand("gm").setExecutor(new GamemodeCommand());
            getCommand("discord").setExecutor(new discord());

            weather weatherCommand = new weather();
            getCommand("setweather").setExecutor(weatherCommand);
            getCommand("setweather").setTabCompleter(weatherCommand);

            time timeCommand = new time();
            getCommand("settime").setExecutor(timeCommand);
            getCommand("settime").setTabCompleter(timeCommand);

            getCommand("tpa").setExecutor(new tpaCommand());
            getCommand("tpyes").setExecutor(new AcceptCommand());
            getCommand("tp").setExecutor(new tpCommand());
            getCommand("tphere").setExecutor(new tphereCommand());

            getCommand("setspawn").setExecutor(new setSpawn());
            getCommand("spawn").setExecutor(new spawn());
            getCommand("delspawn").setExecutor(new delSpawn());
            getCommand("rtp").setExecutor(new rtpCommand());

            getCommand("reload").setExecutor(new reloadCommand());

            saveResource("messages.yml",false);
            saveResource("gui.yml", false);
            saveResource("starterkit.yml", false);
            saveResource("playerKitData.yml", false);

            messagesFile = new File(getDataFolder(), "messages.yml");
            messages = YamlConfiguration.loadConfiguration(messagesFile);

            kitPlayerDataFile = new File(getDataFolder(), "playerKitData.yml");
            kitPlayerData = YamlConfiguration.loadConfiguration(kitPlayerDataFile);

            starterKitConfigFile = new File(getDataFolder(), "starterkit.yml");
            startKitConfig = YamlConfiguration.loadConfiguration(starterKitConfigFile);

            guiFile = new File(getDataFolder(), "gui.yml");
            gui = YamlConfiguration.loadConfiguration(guiFile);

            locationsFile = new File(getDataFolder(), "locations.yml");
            locations = YamlConfiguration.loadConfiguration(locationsFile);

            getLogger().info("Erfolgreich geladen!");

            // Startet das senden der automatisierten Nachrichten!
            startMessages();

            // Updated das Scoardboard
            update();
        }

        @Override
        public void onDisable() {
            getLogger().info("Wird deaktiviert!");
        }


        public static ServerCoreX getInstance()
        {
            return instance;
        }




        public void saveLocations() {
            try {
                locations.save(locationsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void saveAll()
        {
            // Speicherung von Datein
            saveLocations();
        }

        public void reloadAll()
        {
            // Reload von Datein
            messages = YamlConfiguration.loadConfiguration(messagesFile);
            locations = YamlConfiguration.loadConfiguration(locationsFile);
            gui = YamlConfiguration.loadConfiguration(guiFile);
            startKitConfig = YamlConfiguration.loadConfiguration(starterKitConfigFile);
            kitPlayerData = YamlConfiguration.loadConfiguration(kitPlayerDataFile);
        }

        public File getKitPlayerDataFile()
        {
            return kitPlayerDataFile;
        }
    }
