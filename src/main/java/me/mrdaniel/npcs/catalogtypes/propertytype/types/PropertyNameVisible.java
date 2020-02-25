package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.text.Text;

public class PropertyNameVisible extends PropertyType<Boolean> {

	public PropertyNameVisible() {
		super("NameVisible", "name-visible", GenericArguments.bool(Text.of("name-visible")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return !(npc instanceof Human);
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type != NPCTypes.HUMAN;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityLiving)npc).setAlwaysRenderNameTag(value);
	}
}
