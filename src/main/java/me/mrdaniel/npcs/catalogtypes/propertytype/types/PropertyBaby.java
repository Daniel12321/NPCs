package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Ageable;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyBaby extends PropertyType<Boolean> {

	public PropertyBaby() {
		super("Baby", "baby", GenericArguments.bool(Text.of("baby")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Ageable;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type.isAgeable();
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		if (npc instanceof EntityZombie) {
			((EntityZombie)npc).setChild(value);
		} else {
			((EntityAgeable)npc).setGrowingAge(value ? Integer.MIN_VALUE : 0);
		}

		// Fixes some entities (etc. horses) moving around when changing from baby to adult
		Position pos = npc.getData().getProperty(PropertyTypes.POSITION).get();
		((Entity)npc).setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), pos.getYaw(), pos.getPitch());
	}

	@Override
	public void setHocon(HoconNPCData data, Boolean value) {
		data.baby = value;
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
		return data.baby;
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
