package me.mrdaniel.npcs.io.hocon.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MainConfig {

    @Setting public boolean update_message = true;
    @Setting public String npc_select_message = "&eYou selected an NPC.";
    @Setting public boolean open_menu_on_select = true;
    @Setting public boolean enable_action_system = true;
    @Setting public Messages messages = new Messages();
    @Setting public Storage storage = new Storage();

    @ConfigSerializable
    public static class Messages {
        @Setting public String npc_message_format = "%npc_name%&7: ";
        @Setting public String npc_choice_format = "&6&lChoose: ";
    }

    @ConfigSerializable
    public static class Storage {
        @Setting public String storage_type = "hocon";
    }
}
