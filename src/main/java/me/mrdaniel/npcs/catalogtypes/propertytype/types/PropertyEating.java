package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyEating extends PropertyType<Boolean> {

	public PropertyEating() {
		super("Eating", "eating", GenericArguments.bool(Text.of("eating")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof AbstractHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.HORSE
				|| type == NPCTypes.MULE
				|| type == NPCTypes.DONKEY
				|| type == NPCTypes.LLAMA
				|| type == NPCTypes.SKELETON_HORSE
				|| type == NPCTypes.ZOMBIE_HORSE;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((AbstractHorse)npc).setEatingHaystack(value);
	}
}
