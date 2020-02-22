package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.text.Text;

public class PropertyHorsePattern extends PropertyType<HorsePattern> {

	public PropertyHorsePattern() {
		super("HorsePattern", "horsepattern", GenericArguments.catalogedElement(Text.of("horsepattern"), HorsePattern.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Horse;
	}

	@Override
	public void apply(NPCAble npc, HorsePattern value) {
		((EntityHorse) npc).setHorseVariant(value.getNbtId() + npc.getProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.BROWN).getNbtId());
	}
}
