package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class PropertyNPCType extends PropertyType<NPCType> {

	public PropertyNPCType() {
		super("Type", "type");
	}

	@Override
	public TypeToken<NPCType> getTypeToken() {
		return TypeToken.of(NPCType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, NPCType value) {
		// TODO: Implement changing NPC types
	}
}
