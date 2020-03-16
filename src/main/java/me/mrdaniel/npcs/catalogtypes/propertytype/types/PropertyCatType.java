package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityOcelot;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyCatType extends PropertyType<CatType> {

	public PropertyCatType() {
		super("CatType", "cattype", GenericArguments.catalogedElement(Text.of("cattype"), CatType.class));
	}

	@Override
	public TypeToken<CatType> getTypeToken() {
		return TypeToken.of(CatType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityOcelot;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.OCELOT;
	}

	@Override
	public void apply(NPCAble npc, CatType value) {
		((EntityOcelot)npc).setTameSkin(value.getNbtId());
	}

	@Override
	public void setHocon(HoconNPCData data, CatType value) {
		data.cattype = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, CatType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, CatType value) {

	}

	@Nullable
	@Override
	public CatType getHocon(HoconNPCData data) {
		return data.cattype;
	}

	@Nullable
	@Override
	public CatType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public CatType getNBT(NBTNPCData data) {
		return null;
	}
}
