package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyNameVisible extends PropertyType<Boolean> {

	public PropertyNameVisible() {
		super("NameVisible", "name-visible", GenericArguments.bool(Text.of("name-visible")));
	}

	@Override
	public TypeToken<Boolean> getTypeToken() {
		return TypeToken.of(Boolean.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return !(npc instanceof Human);
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type != NPCTypes.HUMAN;
	}

	@Override
	public void apply(NPCAble npc, Boolean value) {
		((EntityLiving)npc).setAlwaysRenderNameTag(value);
	}

	@Override
	public void setHocon(HoconNPCData data, Boolean value) {
		data.nameAlwaysVisible = value;
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
		return data.nameAlwaysVisible;
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
