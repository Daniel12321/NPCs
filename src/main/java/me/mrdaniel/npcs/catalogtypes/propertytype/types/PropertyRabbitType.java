package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityRabbit;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyRabbitType extends PropertyType<RabbitType> {

	public PropertyRabbitType() {
		super("RabbitType", "rabbittype", GenericArguments.catalogedElement(Text.of("rabbittype"), RabbitType.class));
	}

	@Override
	public TypeToken<RabbitType> getTypeToken() {
		return TypeToken.of(RabbitType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityRabbit;
	}

	@Override
	public void apply(NPCAble npc, RabbitType value) {
		((EntityRabbit) npc).setRabbitType(value.getNbtId());
	}
}
