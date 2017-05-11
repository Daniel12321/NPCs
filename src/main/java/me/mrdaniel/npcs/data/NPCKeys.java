package me.mrdaniel.npcs.data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.data.npc.actions.Action;

@SuppressWarnings("serial")
public class NPCKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<Value<Integer>> STARTUP = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>(){}, DataQuery.of("startup"), "npc:startup", "NPC Startup");
	public static final Key<Value<Integer>> ID = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>(){}, DataQuery.of("id"), "npc:id", "NPC Id");
	public static final Key<Value<String>> SKIN = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("skin"), "npc:skin", "NPC Skin");
	public static final Key<Value<Boolean>> LOOKING = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("looking"), "npc:looking", "NPC Looking");
	public static final Key<Value<Boolean>> INTERACT = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("interact"), "npc:interact", "NPC Interact");

	public static final Key<ListValue<Action>> ACTIONS = KeyFactory.makeListKey(new TypeToken<List<Action>>(){}, new TypeToken<ListValue<Action>>(){}, DataQuery.of("actions"), "npc:actions", "NPC Actions");
	public static final Key<MapValue<UUID, Integer>> CURRENT = KeyFactory.makeMapKey(new TypeToken<Map<UUID, Integer>>(){}, new TypeToken<MapValue<UUID, Integer>>(){}, DataQuery.of("current"), "npc:current", "NPC Current");
}