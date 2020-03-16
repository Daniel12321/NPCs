package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

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
		((Living)npc).offer(Keys.DISPLAY_NAME, TextUtils.toText(value));

		// Fixes human NPCs losing some deserialize their properties when changing their skin
		if (npc instanceof Human) {
			npc.refreshEquipment();
		}
	}

	@Override
	public void setHocon(HoconNPCData data, String value) {
		data.name = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, String value) {

	}

	@Override
	public void setNBT(NBTNPCData data, String value) {

	}

	@Nullable
	@Override
	public String getHocon(HoconNPCData data) {
		return data.name;
	}

	@Nullable
	@Override
	public String getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public String getNBT(NBTNPCData data) {
		return null;
	}
}
