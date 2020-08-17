package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

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
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.HUMAN;
	}

	@Override
	public void apply(NPCAble npc, String value) {
		NPCs plugin = NPCs.getInstance();
		Task.builder()
				.async()
				.execute(() -> NPCs.getInstance().getGame().getServer().getGameProfileManager().get(value).thenAccept(gp ->
						Task.builder().execute(() -> npc.setProperty(PropertyTypes.SKIN_UUID, gp.getUniqueId()).save()).submit(plugin)))
				.submit(plugin);
	}

	@Override
	public void setHocon(HoconNPCData data, String value) {
		data.skin.name = value;
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
		return data.skin.name;
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
