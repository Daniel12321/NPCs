package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.scheduler.Task;

import java.util.UUID;

public class PropertySkinUUID extends PropertyType<UUID> {

	public PropertySkinUUID() {
		super("SkinUUID", "skin-uuid", new Object[]{"skin", "uuid"});
	}

	@Override
	public TypeToken<UUID> getTypeToken() {
		return TypeToken.of(UUID.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Human;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.HUMAN;
	}

	@Override
	public void apply(NPCAble npc, UUID value) {
		((Human)npc).offer(Keys.SKIN_UNIQUE_ID, value);

		// Fixes human NPCs losing some deserialize their properties when changing their skin
		npc.refreshEquipment();
	}
}
