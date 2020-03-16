package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityHorse;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyHorseColor extends PropertyType<HorseColor> {

	public PropertyHorseColor() {
		super("HorseColor", "horsecolor", GenericArguments.catalogedElement(Text.of("horsecolor"), HorseColor.class));
	}

	@Override
	public TypeToken<HorseColor> getTypeToken() {
		return TypeToken.of(HorseColor.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityHorse;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.HORSE;
	}

	@Override
	public void apply(NPCAble npc, HorseColor value) {
		((EntityHorse)npc).setHorseVariant(value.getNbtId() + npc.getData().getProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getNbtId());
	}

	@Override
	public void setHocon(HoconNPCData data, HorseColor value) {
		data.horsecolor = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, HorseColor value) {

	}

	@Override
	public void setNBT(NBTNPCData data, HorseColor value) {

	}

	@Nullable
	@Override
	public HorseColor getHocon(HoconNPCData data) {
		return data.horsecolor;
	}

	@Nullable
	@Override
	public HorseColor getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public HorseColor getNBT(NBTNPCData data) {
		return null;
	}
}
