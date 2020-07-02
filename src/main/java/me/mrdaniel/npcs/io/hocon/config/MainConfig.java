package me.mrdaniel.npcs.io.hocon.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MainConfig {

    @Setting public boolean updateMessage = true;
    @Setting public String npcSelectMessage = "&eYou selected an NPC.";
    @Setting public boolean openMenuOnSelect = true;
    @Setting public boolean enableActionSystem = true;
    @Setting public Messages messages = new Messages();
    @Setting public Storage storage = new Storage();

    @ConfigSerializable
    public static class Messages {
        @Setting public String npcMessageFormat = "%npc_name%&7: ";
        @Setting public String npcChoiceFormat = "&6&lChoose: ";
    }

    @ConfigSerializable
    public static class Storage {
        @Setting public String storageType = "hocon";
    }
}
