package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

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
}
