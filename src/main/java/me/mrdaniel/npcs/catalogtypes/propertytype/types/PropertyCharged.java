package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
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
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Creeper;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((Living) npc).offer(Keys.CREEPER_CHARGED, value);
	}
}
