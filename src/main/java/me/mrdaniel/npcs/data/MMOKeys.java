package me.mrdaniel.npcs.data;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.data.value.mutable.OptionalValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;

import me.mrdaniel.npcs.data.action.NPCAction;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<Value<Boolean>> LOOKING = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("looking"), "npc:looking", "NPC Looking");
	public static final Key<Value<Boolean>> INTERACT = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("interact"), "npc:interact", "NPC Interact");

	public static final Key<OptionalValue<NPCAction>> ACTION = KeyFactory.makeOptionalKey(new TypeToken<Optional<NPCAction>>(){}, new TypeToken<OptionalValue<NPCAction>>(){}, DataQuery.of("action"), "npc:action", "NPC Action");

	public static final Key<ListValue<Text>> MESSAGES = KeyFactory.makeListKey(new TypeToken<List<Text>>(){}, new TypeToken<ListValue<Text>>(){}, DataQuery.of("messages"), "npc:messages", "NPC Messages");
	public static final Key<ListValue<String>> COMMANDS = KeyFactory.makeListKey(new TypeToken<List<String>>(){}, new TypeToken<ListValue<String>>(){}, DataQuery.of("commands"), "npc:commands", "NPC Commands");
	public static final Key<Value<Boolean>> RANDOM = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("random"), "npc:random", "NPC Random");
	public static final Key<Value<Boolean>> ALL = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("all"), "npc:all", "NPC All");
	public static final Key<Value<Integer>> CURRENT = KeyFactory.makeSingleKey(TypeToken.of(Integer.class), new TypeToken<Value<Integer>>(){}, DataQuery.of("current"), "npc:current", "NPC Current");

//	public static final Key<ListValue<Vector3d>> WALK_POSITIONS = KeyFactory.makeListKey(new TypeToken<List<Vector3d>>(){}, new TypeToken<ListValue<Vector3d>>(){}, DataQuery.of("walk_positions"), "npc:walk_positions", "NPC Walk Positions");
}