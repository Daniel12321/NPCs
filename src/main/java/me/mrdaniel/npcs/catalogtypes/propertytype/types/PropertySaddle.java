package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityPig;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertySaddle extends PropertyType<Boolean> {

	public PropertySaddle() {
		super("Saddle", "saddle", GenericArguments.bool(Text.of("saddle")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityPig || npc instanceof AbstractHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.PIG
				|| type == NPCTypes.HORSE
				|| type == NPCTypes.MULE
				|| type == NPCTypes.DONKEY
				|| type == NPCTypes.LLAMA
				|| type == NPCTypes.SKELETON_HORSE
				|| type == NPCTypes.ZOMBIE_HORSE;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		if (npc instanceof EntityPig) {
			((EntityPig)npc).setSaddled(value);
		} else if (npc instanceof AbstractHorse) {
			((AbstractHorse)npc).setHorseSaddled(value);
		}
	}

	@Override
	public void setHocon(HoconNPCData data, Boolean value) {
		data.saddle = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, Boolean value) {

	}

	@Override
	public void setNBT(NBTNPCData data, Boolean value) {

	}

	@Nullable
	@Override
	public Boolean getHocon(HoconNPCData data) {
		return data.saddle;
	}

	@Nullable
	@Override
	public Boolean getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public Boolean getNBT(NBTNPCData data) {
		return null;
	}
}
