package me.mrdaniel.npcs.data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class NPCKeys {

	public static final Key<Value<Integer>> ID = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>(){}, DataQuery.of("id"), "npc:id", "NPC Id");
}