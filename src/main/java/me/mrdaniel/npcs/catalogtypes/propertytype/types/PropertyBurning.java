package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.Entity;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyBurning extends PropertyType<Boolean> {

	public PropertyBurning() {
		super("Burning", "burning", GenericArguments.bool(Text.of("burning")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((Entity)npc).setFire(Integer.MAX_VALUE);
	}
}
