package me.mrdaniel.npcs.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

public class NPCKeys {

    public static final Key<Value<Integer>> NPC_ID = Sponge.getRegistry().createBuilder(Key.Builder.class)
            .type(new TypeToken<Value<Integer>>() {})
            .id("npc-id")
            .name("NPC ID")
            .query(DataQuery.of("npc-id"))
            .build();

    public static final Key<Value<Integer>> BUTTON_INDEX = Sponge.getRegistry().createBuilder(Key.Builder.class)
            .type(new TypeToken<Value<Integer>>() {})
            .id("button-index")
            .name("Button Index")
            .query(DataQuery.of("button-index"))
            .build();

    public static void init() {
        NPC_ID.getName();
        BUTTON_INDEX.getName();
    }
}
