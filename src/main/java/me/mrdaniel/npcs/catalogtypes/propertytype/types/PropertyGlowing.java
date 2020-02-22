package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyGlowing extends PropertyType<Boolean> {

	public PropertyGlowing() {
		super("Glowing", "glowing", GenericArguments.bool(Text.of("glowing")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((Entity) npc).setGlowing(value);

		if (value) {
			npc.getProperty(PropertyTypes.GLOWCOLOR).ifPresent(color -> PropertyTypes.GLOWCOLOR.apply(npc, color));
		}
	}
}
