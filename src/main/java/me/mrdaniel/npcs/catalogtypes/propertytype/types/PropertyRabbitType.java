package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityRabbit;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyRabbitType extends PropertyType<RabbitType> {

	public PropertyRabbitType() {
		super("RabbitType", "rabbittype", GenericArguments.catalogedElement(Text.of("rabbittype"), RabbitType.class));
	}

	@Override
	public TypeToken<RabbitType> getTypeToken() {
		return TypeToken.of(RabbitType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityRabbit;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.RABBIT;
	}

	@Override
	public void apply(NPCAble npc, RabbitType value) {
		((EntityRabbit)npc).setRabbitType(value.getNbtId());
	}

	@Override
	public void setHocon(HoconNPCData data, RabbitType value) {
		data.rabbittype = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, RabbitType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, RabbitType value) {

	}

	@Nullable
	@Override
	public RabbitType getHocon(HoconNPCData data) {
		return data.rabbittype;
	}

	@Nullable
	@Override
	public RabbitType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public RabbitType getNBT(NBTNPCData data) {
		return null;
	}
}
