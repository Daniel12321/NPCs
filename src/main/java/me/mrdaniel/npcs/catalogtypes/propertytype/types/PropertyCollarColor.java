package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityWolf;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyCollarColor extends PropertyType<Boolean> {

	// TODO: Set proper argument type
	public PropertyCollarColor() {
		super("CollarColor", "collarcolor", GenericArguments.catalogedElement(Text.of("glowcolor"), GlowColor.class));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWolf;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
//		((EntityWolf) npc).setCollarColor();
	}
}
