package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertySaddle extends PropertyType<Boolean> {

	public PropertySaddle() {
		super("Saddle", "saddle", GenericArguments.bool(Text.of("saddle")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityPig || npc instanceof AbstractHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.PIG
				|| type == NPCTypes.HORSE
				|| type == NPCTypes.MULE
				|| type == NPCTypes.DONKEY
				|| type == NPCTypes.LLAMA
				|| type == NPCTypes.SKELETON_HORSE
				|| type == NPCTypes.ZOMBIE_HORSE;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		if (npc instanceof EntityPig) {
			((EntityPig)npc).setSaddled(value);
		} else if (npc instanceof AbstractHorse) {
			((AbstractHorse)npc).setHorseSaddled(value);
		}
	}
}
