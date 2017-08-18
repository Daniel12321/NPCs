package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.entity.living.Human;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionSkin extends OptionType<String> {

	public OptionSkin() {
		super("Skin", "skin");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Human;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final String value) {
		npc.setNPCSkin(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final String value) {
		file.setSkinName(value);
	}

	@Override
	public Optional<String> readFromFile(final NPCFile file) {
		return file.getSkinName();
	}
}