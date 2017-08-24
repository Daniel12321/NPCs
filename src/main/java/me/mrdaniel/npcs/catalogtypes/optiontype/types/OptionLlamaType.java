package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityLlama;

public class OptionLlamaType extends OptionType<LlamaType> {

	public OptionLlamaType() {
		super("LlamaType", "llamatype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityLlama;
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