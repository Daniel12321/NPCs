package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.text.Text;

public class PropertyHorseColor extends PropertyType<HorseColor> {

	public PropertyHorseColor() {
		super("HorseColor", "horsecolor", GenericArguments.catalogedElement(Text.of("horsecolor"), HorseColor.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Horse;
	}

	@Override
	public void apply(NPCAble npc, HorseColor value) {
		((EntityHorse) npc).setHorseVariant(value.getNbtId() + npc.getProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getNbtId());
	}
}
