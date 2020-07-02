package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Human;

import javax.annotation.Nullable;
import java.util.UUID;

public class PropertySkinUUID extends PropertyType<UUID> {

	public PropertySkinUUID() {
		super("SkinUUID", "skin-uuid");
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

	@Override
	public void setHocon(HoconNPCData data, UUID value) {
		data.skin.uuid = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, UUID value) {

	}

	@Override
	public void setNBT(NBTNPCData data, UUID value) {

	}

	@Nullable
	@Override
	public UUID getHocon(HoconNPCData data) {
		return data.skin.uuid;
	}

	@Nullable
	@Override
	public UUID getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public UUID getNBT(NBTNPCData data) {
		return null;
	}
}
