package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;

public class PropertyCarries extends PropertyType<BlockType> {

	public PropertyCarries() {
		super("Carries", "carries", GenericArguments.catalogedElement(Text.of("carries"), BlockType.class));
	}

	@Override
	public TypeToken<BlockType> getTypeToken() {
		return TypeToken.of(BlockType.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityEnderman;
	}

	@Override
	public boolean isSupported(NPCType type) {
		return type == NPCTypes.ENDERMAN;
	}

	@Override
	public void apply(NPCAble npc, BlockType value) {
		((EntityEnderman)npc).setHeldBlockState((IBlockState)value.getDefaultState());
	}

	@Override
	public void setHocon(HoconNPCData data, BlockType value) {
		data.carries = value;
	}

	@Override
	public void setDatabase(DatabaseNPCData data, BlockType value) {

	}

	@Override
	public void setNBT(NBTNPCData data, BlockType value) {

	}

	@Nullable
	@Override
	public BlockType getHocon(HoconNPCData data) {
		return data.carries;
	}

	@Nullable
	@Override
	public BlockType getDatabase(DatabaseNPCData data) {
		return null;
	}

	@Nullable
	@Override
	public BlockType getNBT(NBTNPCData data) {
		return null;
	}
}
