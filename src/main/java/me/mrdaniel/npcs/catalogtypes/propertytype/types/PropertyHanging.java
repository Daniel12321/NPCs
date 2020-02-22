package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityBat;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Bat;
import org.spongepowered.api.text.Text;

public class PropertyHanging extends PropertyType<Boolean> {

	public PropertyHanging() {
		super("Hanging", "hanging", GenericArguments.bool(Text.of("hanging")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Bat;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityBat) npc).setIsBatHanging(value);
	}
}
