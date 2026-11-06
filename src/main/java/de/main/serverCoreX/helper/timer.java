package de.main.serverCoreX.helper;

import de.main.serverCoreX.ServerCoreX;
import org.bukkit.Bukkit;

public class timer {

    // Hilfs Funktion
    public static void delay(Runnable task, long ticks) {
        // runTaskLater bedeutet quasi das es ein Timer ist
        Bukkit.getScheduler().runTaskLater(
                ServerCoreX.getInstance(),
                // Aufgabe
                task,
                // Delay
                ticks
        );
    }

    public static void repeat(Runnable task, long ticks)
    {
        Bukkit.getScheduler().runTaskTimer(
                ServerCoreX.getInstance(),
                task,
                ticks,
                ticks
        );
    }
}
