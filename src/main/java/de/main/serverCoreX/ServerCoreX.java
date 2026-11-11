    package de.main.serverCoreX;

    import de.main.serverCoreX.commands.*;
    import de.main.serverCoreX.commands.broadcast.broadcast;
    import de.main.serverCoreX.commands.clearchat.clearChatCommand;
    import de.main.serverCoreX.commands.rewards.belohnung.belohnungCommand;
    import de.main.serverCoreX.commands.rewards.daily.dailyCommand;
    import de.main.serverCoreX.commands.rtp.farmworld.farmweltListener;
    import de.main.serverCoreX.commands.rtp.rtpCommand;
    import de.main.serverCoreX.commands.rtp.rtpListener;
    import de.main.serverCoreX.commands.rtp.spawnProtection;
    import de.main.serverCoreX.commands.spawn.delSpawn;
    import de.main.serverCoreX.commands.spawn.setSpawn;
    import de.main.serverCoreX.commands.spawn.spawn;
    import de.main.serverCoreX.commands.speed.flySpeedCommand;
    import de.main.serverCoreX.commands.speed.walkspeedCommand;
    import de.main.serverCoreX.commands.teleport.AcceptCommand;
    import de.main.serverCoreX.commands.teleport.tpCommand;
    import de.main.serverCoreX.commands.teleport.tpaCommand;
    import de.main.serverCoreX.commands.teleport.tphereCommand;
    import de.main.serverCoreX.commands.vanish.VanishChestAnimation;
    import de.main.serverCoreX.commands.vanish.vanish;
    import de.main.serverCoreX.commands.vanish.vanishListener;
    import de.main.serverCoreX.economy.EconomyManager;
    import de.main.serverCoreX.economy.VaultHook;
    import de.main.serverCoreX.economy.bank.bankCommand;
    import de.main.serverCoreX.economy.bank.bankListener;
    import de.main.serverCoreX.economy.ecoCommand;
    import de.main.serverCoreX.economy.payCommand;
    import de.main.serverCoreX.listener.joinLeave.joinListener;
    import de.main.serverCoreX.listener.chat.messageListener;
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

        public File PlayerDataFile;
        public FileConfiguration PlayerData;

        public File tablistFile;
        public FileConfiguration tablist;

        public File bankFile;
        public FileConfiguration bank;

        public EconomyManager economyManager;

        @Override
        public void onEnable()
        {
            instance = this;

            economyManager = new EconomyManager(this);
            VaultHook.register(this);

            saveDefaultConfig();
            Bukkit.getPluginManager().registerEvents(new RestartCommand(),this);
            Bukkit.getPluginManager().registerEvents(new HealCommand(),this);
            Bukkit.getPluginManager().registerEvents(new joinListener(),this);
            Bukkit.getPluginManager().registerEvents(new rtpListener(),this);
            Bukkit.getPluginManager().registerEvents(new spawnProtection(),this);
            Bukkit.getPluginManager().registerEvents(new farmweltListener(),this);
            Bukkit.getPluginManager().registerEvents(new bankListener(),this);
            Bukkit.getPluginManager().registerEvents(new messageListener(),this);

            getCommand("fly").setExecutor(new FlyCommand());
            getCommand("belohnung").setExecutor(new belohnungCommand());
            getCommand("daily").setExecutor(new dailyCommand());
            getCommand("gm").setExecutor(new GamemodeCommand());
            getCommand("discord").setExecutor(new discord());
            getCommand("size").setExecutor(new sizeCommand());
            getCommand("rtp").setExecutor(new rtpCommand());
            getCommand("vanish").setExecutor(new vanish());
            getCommand("reload").setExecutor(new reloadCommand());

            weather weatherCommand = new weather();
            getCommand("setweather").setExecutor(weatherCommand);
            getCommand("setweather").setTabCompleter(weatherCommand);

            time timeCommand = new time();
            getCommand("settime").setExecutor(timeCommand);
            getCommand("settime").setTabCompleter(timeCommand);

            walkspeedCommand walkspeedCommand = new walkspeedCommand();
            getCommand("walkspeed").setExecutor(walkspeedCommand);
            getCommand("walkspeed").setTabCompleter(walkspeedCommand);

            flySpeedCommand flySpeedCommand = new flySpeedCommand();
            getCommand("flyspeed").setExecutor(flySpeedCommand);
            getCommand("flyspeed").setTabCompleter(flySpeedCommand);

            getCommand("tpa").setExecutor(new tpaCommand());
            getCommand("tpyes").setExecutor(new AcceptCommand());
            getCommand("tp").setExecutor(new tpCommand());
            getCommand("tphere").setExecutor(new tphereCommand());

            getCommand("setspawn").setExecutor(new setSpawn());
            getCommand("spawn").setExecutor(new spawn());
            getCommand("delspawn").setExecutor(new delSpawn());

            getCommand("bank").setExecutor(new bankCommand());

            ecoCommand ecoCommand = new ecoCommand();
            getCommand("eco").setExecutor(ecoCommand);
            getCommand("eco").setTabCompleter(ecoCommand);

            getCommand("broadcast").setExecutor(new broadcast());
            getCommand("pay").setExecutor(new payCommand());
            getCommand("clearchat").setExecutor(new clearChatCommand());


            saveResource("messages.yml",false);
            saveResource("bank.yml",false);
            saveResource("gui.yml", false);
            saveResource("starterkit.yml", false);
            saveResource("playerKitData.yml", false);
            saveResource("playerData.yml", false);
            saveResource("tablist.yml", false);

            messagesFile = new File(getDataFolder(), "messages.yml");
            messages = YamlConfiguration.loadConfiguration(messagesFile);

            bankFile = new File(getDataFolder(), "bank.yml");
            bank = YamlConfiguration.loadConfiguration(bankFile);

            kitPlayerDataFile = new File(getDataFolder(), "playerKitData.yml");
            kitPlayerData = YamlConfiguration.loadConfiguration(kitPlayerDataFile);

            PlayerDataFile = new File(getDataFolder(), "playerData.yml");
            PlayerData = YamlConfiguration.loadConfiguration(PlayerDataFile);

            starterKitConfigFile = new File(getDataFolder(), "starterkit.yml");
            startKitConfig = YamlConfiguration.loadConfiguration(starterKitConfigFile);

            guiFile = new File(getDataFolder(), "gui.yml");
            gui = YamlConfiguration.loadConfiguration(guiFile);

            tablistFile = new File(getDataFolder(), "tablist.yml");
            tablist = YamlConfiguration.loadConfiguration(tablistFile);

            locationsFile = new File(getDataFolder(), "locations.yml");
            locations = YamlConfiguration.loadConfiguration(locationsFile);

            getLogger().info("Erfolgreich geladen!");

            // Startet das senden der automatisierten Nachrichten!
            startMessages();

            // Updated das Scoardboard
            update();

            Bukkit.getPluginManager().registerEvents(new vanishListener(), this);
            VanishChestAnimation.register(this);
        }

        @Override
        public void onDisable() {
            getLogger().info("Wird deaktiviert!");

            if (PlayerData != null) {
                saveFile(PlayerDataFile, PlayerData);
            }

            if (economyManager != null) {
                economyManager.save();
            }
        }


        public static ServerCoreX getInstance()
        {
            return instance;
        }


        public void saveFile(File file, FileConfiguration fileConfiguration)
        {
            if (file == null || fileConfiguration == null)
            {
                Bukkit.getLogger().warning("Etwas ist beim Speichern schiefgelaufen. (Kritisch)");
                return;
            }
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void reloadAll()
        {
            // Reload von Datein
            messages = YamlConfiguration.loadConfiguration(messagesFile);
            locations = YamlConfiguration.loadConfiguration(locationsFile);
            gui = YamlConfiguration.loadConfiguration(guiFile);
            startKitConfig = YamlConfiguration.loadConfiguration(starterKitConfigFile);
            kitPlayerData = YamlConfiguration.loadConfiguration(kitPlayerDataFile);
            PlayerData = YamlConfiguration.loadConfiguration(PlayerDataFile);
            tablist = YamlConfiguration.loadConfiguration(tablistFile);
            bank = YamlConfiguration.loadConfiguration(bankFile);
            reloadConfig();
        }

        public File getPlayerDataFile()
        {
            return PlayerDataFile;
        }
        public File getKitPlayerDataFile()
        {
            return kitPlayerDataFile;
        }
    }
