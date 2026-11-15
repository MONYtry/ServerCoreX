
package de.main.serverCoreX.commands.itemedit;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class itemeditTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        // Erstellung des Leeren Arrays
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            return filter(List.of("set", "add", "clear"), args[0]);
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                return filter(List.of("lore", "infinity", "glint", "attribute", "displayname"), args[1]);
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("attribute")) {

            for (Attribute attribute : Registry.ATTRIBUTE) {
                NamespacedKey key = Registry.ATTRIBUTE.getKey(attribute);

                if (key != null && key.getNamespace().equals("minecraft")) {
                    completions.add(key.getKey());
                }
            }

            return filter(completions, args[2]);
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("attribute")) {
            return filter(List.of("0.1", "1", "5", "10", "20"), args[3]);
        }

        return completions;
    }

    private List<String> filter(List<String> options, String input) {
        return options.stream().filter(s -> s.toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT))).toList();
    }
}
