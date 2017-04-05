package me.mrdaniel.npcs.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.data.action.NPCActions;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<Value<String>> SKIN = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("skin"), "npc:skin", "NPC Skin");
	public static final Key<Value<Boolean>> LOOKING = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("looking"), "npc:looking", "NPC Looking");
	public static final Key<Value<Boolean>> INTERACT = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("interact"), "npc:interact", "NPC Interact");

	public static final Key<Value<NPCActions>> ACTIONS = KeyFactory.makeSingleKey(TypeToken.of(NPCActions.class), new TypeToken<Value<NPCActions>>(){}, DataQuery.of("actions"), "npc:actions", "NPC Actions");
//	public static final Key<ListValue<Action>> ACTIONS = KeyFactory.makeListKey(new TypeToken<List<Action>>(){}, new TypeToken<ListValue<Action>>(){}, DataQuery.of("actions"), "npc:actions", "NPC Actions");

//	public static final Key<Value<String>> ACTION_VALUE = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("actionvalue"), "npc:actionvalue", "NPC Action Value");
//	public static final Key<Value<String>> ACTION_TYPE = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("actiontype"), "npc:actiontype", "NPC Action Type");

//	public static final Key<Value<String>> ACTIONSTYPE = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("actionstype"), "npc:actionstype", "NPC Actions Type");
//	public static final Key<Value<Boolean>> RANDOM = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("random"), "npc:random", "NPC Random");
//	public static final Key<MapValue<UUID, Integer>> CURRENT = KeyFactory.makeMapKey(new TypeToken<Map<UUID, Integer>>(){}, new TypeToken<MapValue<UUID, Integer>>(){}, DataQuery.of("current"), "npc:current", "NPC Current");
}