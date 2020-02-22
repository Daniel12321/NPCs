package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityParrot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Parrot;
import org.spongepowered.api.text.Text;

public class PropertyParrotType extends PropertyType<ParrotType> {

	public PropertyParrotType() {
		super("ParrotType", "parrottype", GenericArguments.catalogedElement(Text.of("parrottype"), ParrotType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Parrot;
	}

	@Override
	public void apply(NPCAble npc, ParrotType value) {
		((EntityParrot) npc).setVariant(value.getNbtId());
	}
}
