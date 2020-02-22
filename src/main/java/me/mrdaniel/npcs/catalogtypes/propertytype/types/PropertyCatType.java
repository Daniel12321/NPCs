package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Ocelot;
import org.spongepowered.api.text.Text;

public class PropertyCatType extends PropertyType<CatType> {

	public PropertyCatType() {
		super("CatType", "cattype", GenericArguments.catalogedElement(Text.of("cattype"), CatType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Ocelot;
	}

	@Override
	public void apply(NPCAble npc, CatType value) {
		((EntityOcelot) npc).setTameSkin(value.getNbtId());
	}
}
