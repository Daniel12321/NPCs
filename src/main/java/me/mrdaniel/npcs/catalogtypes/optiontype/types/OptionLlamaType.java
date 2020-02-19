package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Llama;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionLlamaType extends OptionType<LlamaType> {

	public OptionLlamaType() {
		super("LlamaType", "llamatype", GenericArguments.catalogedElement(Text.of("llamatype"), LlamaType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Llama;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final LlamaType value) {
		npc.setNPCLlamaType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final LlamaType value) {
		file.setLlamaType(value).save();
	}

	@Override
	public Optional<LlamaType> readFromFile(final NPCFile file) {
		return file.getLlamaType();
	}
}
