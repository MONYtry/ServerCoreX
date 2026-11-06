package de.main.serverCoreX.commands.teleport;

import java.util.HashMap;
import java.util.UUID;

public class TeleportManager {

    public static HashMap<UUID, UUID> teleportRequests = new HashMap<>();

    // Wer soll teleportiert werden?
    public static HashMap<UUID, Boolean> teleportHere = new HashMap<>();
}