package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionName extends OptionType<String> {

	public OptionName() {
		super("Name", "name");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final String value) {
		npc.setNPCName(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final String value) {
		file.setName(value).save();
	}

	@Override
	public Optional<String> readFromFile(final NPCFile file) {
		return file.getName();
	}
}