package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

public class PropertyName extends PropertyType<String> {

	public PropertyName() {
		super("Name", "name", GenericArguments.remainingJoinedStrings(Text.of("name")));
	}

	@Override
	public TypeToken<String> getTypeToken() {
		return TypeToken.of(String.class);
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
	public void apply(NPCAble npc, String value) {

		// TODO: Check if this works
		// Reruns the NPC setup
		// No need to apply the value to the NPC directly, as it is reapplied during the setup
		// Fixes human NPCs losing some of their properties when changing their name
		npc.setData(npc.getData());


//		((Living)npc).offer(Keys.DISPLAY_NAME, TextUtils.toText(value));
//		if (npc instanceof Human) {
//			npc.refresh();
//		}
	}
}
