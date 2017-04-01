package me.mrdaniel.npcs.data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class MMOKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<Value<Boolean>> LOOKING = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("looking"), "npc:looking", "NPC Looking");
	public static final Key<Value<Boolean>> INTERACT = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("interact"), "npc:interact", "NPC Interact");

//	public static final Key<ListValue<Vector3d>> WALK_POSITIONS = KeyFactory.makeListKey(new TypeToken<List<Vector3d>>(){}, new TypeToken<ListValue<Vector3d>>(){}, DataQuery.of("walk_positions"), "npc:walk_positions", "NPC Walk Positions");
}