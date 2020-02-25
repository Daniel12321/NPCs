package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityWolf;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyCollarColor extends PropertyType<DyeColorType> {

	public PropertyCollarColor() {
		super("CollarColor", "collarcolor", GenericArguments.catalogedElement(Text.of("collarcolor"), DyeColorType.class));
	}

	@Override
	public TypeToken<DyeColorType> getTypeToken() {
		return TypeToken.of(DyeColorType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWolf;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.WOLF;
	}

	@Override
	public void apply(NPCAble npc, DyeColorType value) {
		((EntityWolf)npc).setTamed(true);
		((EntityWolf)npc).setCollarColor(value.getColor());
	}
}
