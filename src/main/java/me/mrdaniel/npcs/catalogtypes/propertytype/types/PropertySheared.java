package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntitySheep;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertySheared extends PropertyType<Boolean> {

	public PropertySheared() {
		super("Sheared", "sheared", GenericArguments.bool(Text.of("sheared")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
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
	public void apply(NPCAble npc, Boolean value) {
		((EntitySheep)npc).setSheared(value);
	}
}
