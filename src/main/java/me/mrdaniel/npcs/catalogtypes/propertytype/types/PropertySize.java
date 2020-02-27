package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.text.Text;

public class PropertySize extends PropertyType<Integer> {

	public PropertySize() {
		super("Size", "size", GenericArguments.integer(Text.of("size")));
	}

	@Override
	public TypeToken<Integer> getTypeToken() {
		return TypeToken.of(Integer.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Slime;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.SLIME || type == NPCTypes.MAGMA_CUBE;
	}

	@Override
	public void apply(NPCAble npc, Integer value) {
		((Living)npc).offer(Keys.SLIME_SIZE, value);
	}
}
