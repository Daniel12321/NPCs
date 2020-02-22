package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyInteract extends PropertyType<Boolean> {

	public PropertyInteract() {
		super("Interact", "interact", GenericArguments.bool(Text.of("interact")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
	}
}
