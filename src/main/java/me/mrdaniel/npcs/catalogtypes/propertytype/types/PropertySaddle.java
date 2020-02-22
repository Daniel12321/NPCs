package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Pig;
import org.spongepowered.api.text.Text;

public class PropertySaddle extends PropertyType<Boolean> {

	public PropertySaddle() {
		super("Saddle", "saddle", GenericArguments.bool(Text.of("saddle")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Pig;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityPig) npc).setSaddled(value);
	}
}
