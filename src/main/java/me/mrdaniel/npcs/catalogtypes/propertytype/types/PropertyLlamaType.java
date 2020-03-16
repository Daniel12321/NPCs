package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyLlamaType extends PropertyType<LlamaType> {

	public PropertyLlamaType() {
		super("LlamaType", "llamatype", GenericArguments.catalogedElement(Text.of("llamatype"), LlamaType.class));
	}

	@Override
	public TypeToken<LlamaType> getTypeToken() {
		return TypeToken.of(LlamaType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityLlama;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.LLAMA;
	}

	@Override
	public void apply(NPCAble npc, LlamaType value) {
		((EntityLlama)npc).setVariant(value.getNbtId());
	}

	@Override
	public void setHocon(HoconNPCData data, LlamaType value) {
		data.llamatype = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, LlamaType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, LlamaType value) {

	}

	@Nullable
	@Override
	public LlamaType getHocon(HoconNPCData data) {
		return data.llamatype;
	}

	@Nullable
	@Override
	public LlamaType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public LlamaType getNBT(NBTNPCData data) {
		return null;
	}
}
