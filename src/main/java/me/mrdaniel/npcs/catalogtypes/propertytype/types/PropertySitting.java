package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityTameable;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertySitting extends PropertyType<Boolean> {

	public PropertySitting() {
		super("Sitting", "sitting", GenericArguments.bool(Text.of("sitting")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityTameable;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type.isTameable();
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityTameable) npc).setSitting(value);
	}
}
