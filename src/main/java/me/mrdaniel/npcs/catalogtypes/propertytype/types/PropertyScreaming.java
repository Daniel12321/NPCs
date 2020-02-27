package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.monster.EntityEnderman;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;

public class PropertyScreaming extends PropertyType<Boolean> {

	public PropertyScreaming() {
		super("Screaming", "screaming", GenericArguments.bool(Text.of("screaming")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityEnderman;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.ENDERMAN;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((Entity)npc).offer(Keys.IS_SCREAMING, value);
	}
}
