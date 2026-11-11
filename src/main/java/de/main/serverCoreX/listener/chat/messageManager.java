package de.main.serverCoreX.listener.chat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class messageManager {
    public static Set<UUID> chatCooldown = ConcurrentHashMap.newKeySet();
}
