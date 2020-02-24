package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.monster.EntitySnowman;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyPumpkin extends PropertyType<Boolean> {

	public PropertyPumpkin() {
		super("Pumpkin", "pumpkin", GenericArguments.bool(Text.of("pumpkin")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntitySnowman;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.SNOWMAN;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntitySnowman) npc).setPumpkinEquipped(value);
	}
}
