package de.main.serverCoreX.helper;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.main.serverCoreX.helper.permissionCheck.hasPermission;
import static de.main.serverCoreX.helper.placeholder.replacePlaceholder;

public class ItemCreator {

    public static void createCustomModelInventory(Inventory inventoryToUse,FileConfiguration cfgToUse, Player p)
    {
        FileConfiguration cfg = cfgToUse;
        ConfigurationSection items = cfg.getConfigurationSection("items");

        int index = 0;

        for (String key : items.getKeys(false))
        {
            String path = "items." + key;
            String materialName = cfg.getString(path + ".material");
            String name = cfg.getString(path + ".name");

            String rarity = cfg.getString(path + ".rarity");
            if (rarity == null)
                rarity = "not found!";

            List<String> lore = cfg.getStringList(path + ".lore");
            if (lore.isEmpty())
                lore = new ArrayList<>();


            Material material;
            try {
                material = Material.getMaterial(materialName);
            } catch (Exception e) {
                material = Material.STONE;
            }

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            List<String> enchantments = cfg.getStringList(path + ".enchantments");
            if (enchantments != null)
            {
                for (String enchant : enchantments)
                {
                    String[] split = enchant.split(" ");
                    Enchantment enchantment = Enchantment.getByName(split[0]);
                    int level = 1;

                    if (split[1] != null)
                        level = Integer.parseInt(split[1]);

                    if (enchantment != null)
                        meta.addEnchant(enchantment, level,true);
                }
            }

            // Speicherung des Config-Keys
            NamespacedKey configKey = new NamespacedKey(ServerCoreX.getInstance(), "config_key");
            meta.getPersistentDataContainer().set(configKey, PersistentDataType.STRING, key);

            String nbt = cfg.getString(path + ".nbtValue",null);
            if (nbt != null)
            {
                NamespacedKey nbtKey = new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class),"custom_item");
                meta.getPersistentDataContainer().set(nbtKey, PersistentDataType.STRING, nbt);
            }

            NamespacedKey getNbtKey = new NamespacedKey(ServerCoreX.getPlugin(ServerCoreX.class),"custom_item");
            String value = meta.getPersistentDataContainer().get(getNbtKey, PersistentDataType.STRING);

            List<String> dings = cfg.getStringList(path + ".description");
            if (dings != null)
            {
                for (String line : dings)
                    lore.add(line);
            }

            // Paths
            String raritiesPath = "rarities.";
            String errorPath = cfg.getString(raritiesPath + "error","Error while getting Config-Connection");

            String displayRarity = cfg.getString(raritiesPath + "display", "§7Seltenheit §8» §e");
            String displaytType = cfg.getString(path + ".type","Fehler bitte Type in der Config.yml angeben!");

            // Fügt eine Seltenheits-Lore in die Lore ein
            lore.add(displayRarity + cfg.getString(raritiesPath + rarity.toLowerCase(),errorPath));
            lore.add("§7Typ §8» " + displaytType);

            if (hasPermission(p,"customItems.use"))
            {
                lore.add("§e");
                lore.add("§7Klicke hier, um das Item zu entnehmen!");
                lore.add("§e");
            }

            meta.setDisplayName(name);
            meta.setLore(lore);

            String rawModel = cfg.getString(path + ".customModelName");

            if (rawModel != null)
            {
                rawModel = rawModel.replace("%texturePackName%",cfg.getString("customTextures.texturepackName","kasai"));

                String[] modelFinal = rawModel.split(" ");

                if (rawModel.isEmpty() || modelFinal[0] == null || modelFinal[1] == null)
                    continue;

                // Setzt das Item-Model
                meta.setItemModel(NamespacedKey.fromString(modelFinal[0]));

                // Custom Model Data
                CustomModelDataComponent data = meta.getCustomModelDataComponent();
                data.setStrings(List.of(modelFinal[1]));
                meta.setCustomModelDataComponent(data);

            }

            item.setItemMeta(meta);
            inventoryToUse.setItem(index,item);
            index++;
        }
    }


    public static void autoGenItemsWithConfigGUI(Inventory inventoryToUse,FileConfiguration cfgToUse, Player p)
    {
        FileConfiguration cfg = cfgToUse;

        ConfigurationSection items = cfg.getConfigurationSection("items");

        // Geht durch jede Kategorie
        for (String key : items.getKeys(false)) {

            // Dynamischer Path
            String path = "items." + key;

            // Material Name
            String materialName = cfg.getString(path + ".material");

            // Slot index erstellen
            int slot = cfg.getInt(path + ".slot");

            // Item DisplayName
            String name = cfg.getString(path + ".name");

            // Item Lore
            List<String> lore = cfg.getStringList(path + ".lore");
            if (p != null)
            {
                replacePlaceholder((ArrayList<String>) lore,p);
            }

            // Ignorieren
            Material material;
            try {
                // Versucht das material zu switchen
                material = Material.valueOf(materialName);
            } catch (Exception e) {
                // Falls Error
                material = Material.STONE;
                System.out.print("error Critical!");
            }

            ItemStack item;
            if (cfg.contains(path + ".texture"))
            {
                String texture = cfg.getString(path + ".texture");
                item = createHead(texture);
            }
            else
                item = new ItemStack(material);

            ItemMeta meta = item.getItemMeta();

            if (name != null)
                meta.setDisplayName(name);

            if (!lore.isEmpty())
                meta.setLore(lore);

            String action = cfg.getString(path + ".action");

            if (action != null)
                meta.getPersistentDataContainer().set(new NamespacedKey(ServerCoreX.getInstance(), "action"), PersistentDataType.STRING, action);


            boolean customModelData = cfg.getBoolean(path + ".UseCustomModel");
            if (customModelData)
            {
                String rawModel = cfg.getString(path + ".customModelName");
                String[] modelFinal = rawModel.split(" ");

                if (rawModel.isEmpty() || modelFinal[0] == null || modelFinal[1] == null)
                    continue;

                // Deine Item-Definition
                meta.setItemModel(NamespacedKey.fromString(modelFinal[0]));

                // Custom Model Data
                CustomModelDataComponent data = meta.getCustomModelDataComponent();
                data.setStrings(List.of(modelFinal[1]));
                meta.setCustomModelDataComponent(data);
            }

            item.setItemMeta(meta);
            inventoryToUse.setItem(slot, item);
        }
    }


    public static void giveItemsWithConfig(Player p, FileConfiguration cfg) {

        ConfigurationSection items = cfg.getConfigurationSection("items");
        if (items == null) return;

        for (String key : items.getKeys(false)) {

            String path = "items." + key;

            String materialName = cfg.getString(path + ".material");
            if (materialName == null) continue;

            // Holt sich die Anzahl
            int amount = cfg.getInt(path + ".amount", 1);

            // Erstellt leeres Material-Objekt
            Material material;
            try {
                material = Material.valueOf(materialName.toUpperCase());
            } catch (Exception e) {
                System.out.println("Ungültiges Material: " + materialName);
                material = Material.STONE;
                continue;
            }

            // Erstellt das Item mit Anzahl
            ItemStack item = new ItemStack(material, amount);

            // Erstellt die ItemMeta
            ItemMeta itemMeta = item.getItemMeta();

            String displayName = cfg.getString(path + ".displayname");
            if (displayName != null)
                itemMeta.setDisplayName(displayName);

            List<String> enchantments = cfg.getStringList(path + ".enchantments");
            for (String enchant : enchantments)
            {
                String[] split = enchant.split(" ");
                Enchantment enchantment = Enchantment.getByName(split[0]);
                int level = 1;

                if (split[1] != null)
                    level = Integer.parseInt(split[1]);

                if (enchantment != null)
                    itemMeta.addEnchant(enchantment,level,true);
            }

            // Holt sich die Lore
            List<String> lore = cfg.getStringList(path + ".lore");
            // Setzt Lore falls sie existiert
            if (lore != null || !lore.isEmpty())
                itemMeta.setLore(lore);


            item.setItemMeta(itemMeta);

            // Holt sich den Slot
            String armorSlot = cfg.getString(path + ".slot");

            // Falls ein Slot gefunden wurde
            if (armorSlot != null)
            {
                switch (armorSlot.toUpperCase()) {

                    case "HELMET":
                        p.getInventory().setHelmet(item);
                        break;

                    case "CHESTPLATE":
                        p.getInventory().setChestplate(item);
                        break;

                    case "LEGGINGS":
                        p.getInventory().setLeggings(item);
                        break;

                    case "BOOTS":
                        p.getInventory().setBoots(item);
                        break;
                    // Falls es keine Rüstung ist
                    default:
                        try {
                            int slot = Integer.parseInt(armorSlot);
                            p.getInventory().setItem(slot, item);
                        } catch (NumberFormatException e) {
                            System.out.println("Ungültiger Slot: " + armorSlot);
                        }
                        break;
                }
            }
            else {
                p.getInventory().addItem(item);
            }
        }
    }

    public static ItemStack createHead(String texture) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) item.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());

        profile.setProperty(new ProfileProperty(
                "textures", texture
        ));

        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }
}
