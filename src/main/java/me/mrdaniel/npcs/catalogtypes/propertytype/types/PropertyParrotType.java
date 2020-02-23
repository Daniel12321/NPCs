package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityParrot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyParrotType extends PropertyType<ParrotType> {

	public PropertyParrotType() {
		super("ParrotType", "parrottype", GenericArguments.catalogedElement(Text.of("parrottype"), ParrotType.class));
	}

	@Override
	public TypeToken<ParrotType> getTypeToken() {
		return TypeToken.of(ParrotType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityParrot;
	}

	@Override
	public void apply(NPCAble npc, ParrotType value) {
		((EntityParrot) npc).setVariant(value.getNbtId());
	}
}
