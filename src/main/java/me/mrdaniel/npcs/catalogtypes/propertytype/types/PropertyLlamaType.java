package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

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
}
