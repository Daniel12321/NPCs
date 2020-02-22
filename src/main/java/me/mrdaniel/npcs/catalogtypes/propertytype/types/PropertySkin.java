package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

public class PropertySkin extends PropertyType<String> {

	public PropertySkin() {
		super("Skin", "skin", GenericArguments.string(Text.of("skin")));
	}

	@Override
	public TypeToken<String> getTypeToken() {
		return TypeToken.of(String.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Human;
	}

	@Override
	public void apply(NPCAble npc, String value) {
		NPCs plugin = NPCs.getInstance();
		Task.builder().async().execute(() -> {
			NPCs.getInstance().getGame().getServer().getGameProfileManager().get(value).thenAccept(gp -> {
				Task.builder().execute(() -> npc.setProperty(PropertyTypes.SKIN_UUID, gp.getUniqueId())).submit(plugin);
			});
		})
		.submit(plugin);
	}
}
