package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.passive.EntityLlama;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Llama;
import org.spongepowered.api.text.Text;

public class PropertyLlamaType extends PropertyType<LlamaType> {

	public PropertyLlamaType() {
		super("LlamaType", "llamatype", GenericArguments.catalogedElement(Text.of("llamatype"), LlamaType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Llama;
	}

	@Override
	public void apply(NPCAble npc, LlamaType value) {
		((EntityLlama) npc).setVariant(value.getNbtId());
	}
}
