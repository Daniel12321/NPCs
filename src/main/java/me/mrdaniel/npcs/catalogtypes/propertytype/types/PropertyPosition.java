package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public class PropertyPosition extends PropertyType<Position> {

	public PropertyPosition() {
		super("Position", "position");
	}

	@Override
	public TypeToken<Position> getTypeToken() {
		return TypeToken.of(Position.class);
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
	public void apply(NPCAble npc, Position value) {
		((Entity)npc).setPositionAndRotation(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
	}

	@Override
	public void setHocon(HoconNPCData data, Position value) {
		data.position = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, Position value) {

	}

	@Override
	public void setNBT(NBTNPCData data, Position value) {

	}

	@Nullable
	@Override
	public Position getHocon(HoconNPCData data) {
		return data.position;
	}

	@Nullable
	@Override
	public Position getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public Position getNBT(NBTNPCData data) {
		return null;
	}
}
