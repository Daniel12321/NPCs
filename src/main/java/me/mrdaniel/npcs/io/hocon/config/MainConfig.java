package me.mrdaniel.npcs.io.hocon.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MainConfig {

    @Setting public boolean updateMessage = true;
    @Setting public String npcSelectMessage = "&eYou selected an NPC.";
    @Setting public boolean openMenuOnSelect = true;
    @Setting public Messages messages = new Messages();
    @Setting public Storage storage = new Storage();

    public boolean isUpdateMessage() {
        return updateMessage;
    }

    public String getNpcSelectMessage() {
        return npcSelectMessage;
    }

    public boolean isOpenMenuOnSelect() {
        return openMenuOnSelect;
    }

    public Messages getMessages() {
        return messages;
    }

    public Storage getStorage() {
        return storage;
    }

    @ConfigSerializable
    public static class Messages {
        public String npcMessageFormat = "%npc_name%&7: ";
        public String npcChoiceFormat = "&6&lChoose: ";

        public String getNpcMessageFormat() {
            return npcMessageFormat;
        }

        public String getNpcChoiceFormat() {
            return npcChoiceFormat;
        }
    }

    @ConfigSerializable
    public static class Storage {
        public String storageType = "hocon";

        public String getStorageType() {
            return storageType;
        }
    }
}
