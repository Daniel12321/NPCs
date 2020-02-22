package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityWolf;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Wolf;
import org.spongepowered.api.text.Text;

public class PropertyAngry extends PropertyType<Boolean> {

	public PropertyAngry() {
		super("Angry", "angry", GenericArguments.bool(Text.of("angry")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Wolf;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityWolf) npc).setAngry(value);
	}
}
