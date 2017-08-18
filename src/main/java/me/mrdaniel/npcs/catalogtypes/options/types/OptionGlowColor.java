package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.text.format.TextColor;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionGlowColor extends OptionType<TextColor> {

	public OptionGlowColor() {
		super("GlowColor", "glowcolor");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final TextColor value) {
		npc.setNPCGlowColor(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final TextColor value) {
		file.setGlowColor(value);
	}

	@Override
	public Optional<TextColor> readFromFile(final NPCFile file) {
		return file.getGlowColor();
	}
}