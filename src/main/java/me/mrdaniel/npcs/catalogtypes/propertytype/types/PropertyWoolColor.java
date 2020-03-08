package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntitySheep;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyWoolColor extends PropertyType<DyeColorType> {

	public PropertyWoolColor() {
		super("WoolColor", "woolcolor", GenericArguments.catalogedElement(Text.of("woolcolor"), DyeColorType.class));
	}

	@Override
	public TypeToken<DyeColorType> getTypeToken() {
		return TypeToken.of(DyeColorType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntitySheep;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.SHEEP;
	}

	@Override
	public void apply(NPCAble npc, DyeColorType value) {
		((EntitySheep)npc).setFleeceColor(value.getColor());
	}
}
