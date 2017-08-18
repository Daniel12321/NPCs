package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.LlamaVariant;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityLlama;

public class OptionLlamaType extends OptionType<LlamaVariant> {

	public OptionLlamaType() {
		super("LlamaType", "llamatype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityLlama;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final LlamaVariant value) {
		npc.setNPCLlamaType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final LlamaVariant value) {
		file.setLlamaType(value);
	}

	@Override
	public Optional<LlamaVariant> readFromFile(final NPCFile file) {
		return file.getLlamaType();
	}
}