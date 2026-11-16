package de.main.serverCoreX.helper;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import static de.main.serverCoreX.prefix.PrefixSettings.getCustomPrefix;


public class customImages {

    public static String getPrefix(Player p)
    {
        return PlaceholderAPI.setPlaceholders(p,"%luckperms_prefix%");
    }

    public static Component getDisplayRank(Player p, boolean addUsername)
    {

        String prefix = getPrefix(p) + " §7| §f";
        if (addUsername)
            prefix = getPrefix(p) + " §7| §f" + p.getName();

        Component headerComponent = Component.empty();

        if (getCustomPrefix(p.getUniqueId()))
        {
            String imageToUse = null;
            String cleanRank = getPrefix(p).replaceAll("§.","");

            switch (cleanRank.toLowerCase())
            {
                case "owner":
                    imageToUse = "\uE006";
                    break;
                default:
                    imageToUse = null;
                    break;
            }

            if (imageToUse == null)
                return Component.empty().append(Component.text(prefix));;

            Component logo = Component.text(imageToUse).font(Key.key("kasai", "default"));

            if (addUsername)
                headerComponent = Component.empty().append(logo).append(Component.text(" §7| §f" + p.getName()));
            else
                headerComponent = Component.empty().append(logo).append(Component.text(" §7 | §f"));
        }
        else
            headerComponent = Component.empty().append(Component.text(prefix));
        return headerComponent;
    }



    public static void setTextureInScoreboard(Objective objective, Scoreboard board,String emptyString, String boardTeam, String fontKey, String TexturepacketName, int score)
    {
        String newEntry = emptyString;
        Team team = board.getTeam(boardTeam);
        if (team == null)
            team = board.registerNewTeam(boardTeam);

        team.addEntry(newEntry);
        team.prefix(Component.text(fontKey).font(Key.key(TexturepacketName, "default")));

        objective.getScore(newEntry).setScore(score);
    }
}
