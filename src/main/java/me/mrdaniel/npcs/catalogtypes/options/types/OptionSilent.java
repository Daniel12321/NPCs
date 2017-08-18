package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionSilent extends OptionType<Boolean> {

	public OptionSilent() {
		super("Silent", "silent");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCSilent(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setSilent(value);
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getSilent());
	}
}