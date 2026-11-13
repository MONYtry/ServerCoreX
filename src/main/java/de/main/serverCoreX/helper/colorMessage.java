package de.main.serverCoreX.helper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class colorMessage {
    public static Component handleColor(String rawMessage)
    {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(rawMessage);
    }
}
