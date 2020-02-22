package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyNameVisible extends PropertyType<Boolean> {

	public PropertyNameVisible() {
		super("NameVisible", "name-visible", GenericArguments.bool(Text.of("angry")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityLiving) npc).setAlwaysRenderNameTag(value);
	}
}
