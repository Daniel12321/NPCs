package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionInteract extends OptionType<Boolean> {

	public OptionInteract() {
		super("Interact", "interact");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCInteract(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setInteract(value);
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getInteract());
	}
}