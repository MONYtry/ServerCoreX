    package de.main.serverCoreX;

    import de.main.serverCoreX.commands.*;
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
    import org.yaml.snakeyaml.constructor.SafeConstructor;

    import java.io.File;
    import java.io.IOException;

    import static de.main.serverCoreX.BroadcastManager.startMessages;

    public final class ServerCoreX extends JavaPlugin {

        public static ServerCoreX instance;
        public FileConfiguration messages;
        public FileConfiguration locations;
        public File locationsFile;
        public File messagesFile;


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

            getCommand("reload").setExecutor(new reloadCommand());

            saveResource("messages.yml",false);

            messagesFile = new File(getDataFolder(), "messages.yml");
            messages = YamlConfiguration.loadConfiguration(messagesFile);

            locationsFile = new File(getDataFolder(), "locations.yml");
            locations = YamlConfiguration.loadConfiguration(locationsFile);

            getLogger().info("Erfolgreich geladen!");

            // Startet das senden der automatisierten Nachrichten!
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

        public void saveMessages()
        {
            try {
                messages.save(messagesFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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
            saveMessages();
            saveLocations();
        }

        public void reloadAll()
        {
            // Reload von Datein
            messages = YamlConfiguration.loadConfiguration(messagesFile);
            locations = YamlConfiguration.loadConfiguration(locationsFile);
        }
    }
