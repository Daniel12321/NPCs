package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityWolf;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyAngry extends PropertyType<Boolean> {

	public PropertyAngry() {
		super("Angry", "angry", GenericArguments.bool(Text.of("angry")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWolf;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.WOLF;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityWolf)npc).setAngry(value);
	}
}
