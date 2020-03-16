package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityParrot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyParrotType extends PropertyType<ParrotType> {

	public PropertyParrotType() {
		super("ParrotType", "parrottype", GenericArguments.catalogedElement(Text.of("parrottype"), ParrotType.class));
	}

	@Override
	public TypeToken<ParrotType> getTypeToken() {
		return TypeToken.of(ParrotType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityParrot;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.PARROT;
	}

	@Override
	public void apply(NPCAble npc, ParrotType value) {
		((EntityParrot)npc).setVariant(value.getNbtId());
	}

	@Override
	public void setHocon(HoconNPCData data, ParrotType value) {
		data.parrottype = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, ParrotType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, ParrotType value) {

	}

	@Nullable
	@Override
	public ParrotType getHocon(HoconNPCData data) {
		return data.parrottype;
	}

	@Nullable
	@Override
	public ParrotType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public ParrotType getNBT(NBTNPCData data) {
		return null;
	}
}
