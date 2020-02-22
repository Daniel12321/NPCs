package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.monster.EntitySnowman;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.golem.SnowGolem;
import org.spongepowered.api.text.Text;

public class PropertyPumpkin extends PropertyType<Boolean> {

	public PropertyPumpkin() {
		super("Pumpkin", "pumpkin", GenericArguments.bool(Text.of("pumpkin")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof SnowGolem;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntitySnowman) npc).setPumpkinEquipped(value);
	}
}
