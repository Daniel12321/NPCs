package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.text.Text;

public class PropertyCharged extends PropertyType<Boolean> {

	public PropertyCharged() {
		super("Charged", "charged", GenericArguments.bool(Text.of("charged")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Creeper;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.CREEPER;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((Living)npc).offer(Keys.CREEPER_CHARGED, value);
	}
}
