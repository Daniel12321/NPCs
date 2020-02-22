package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

public class PropertyName extends PropertyType<String> {

	public PropertyName() {
		super("Name", "name", GenericArguments.remainingJoinedStrings(Text.of("name")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, String value) {
		((Living) npc).offer(Keys.DISPLAY_NAME, TextUtils.toText(value));

		// TODO: Check if this is needed
//		npc.setAlwaysRenderNameTag(true);

		if (npc.getProperty(PropertyTypes.GLOWING).orElse(false)) {
			npc.getProperty(PropertyTypes.GLOWCOLOR).ifPresent(color -> PropertyTypes.GLOWCOLOR.apply(npc, color));
		}
	}
}
