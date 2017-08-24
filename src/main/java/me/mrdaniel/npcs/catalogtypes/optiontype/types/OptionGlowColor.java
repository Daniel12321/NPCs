package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionGlowColor extends OptionType<GlowColor> {

	public OptionGlowColor() {
		super("GlowColor", "glowcolor");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final GlowColor value) {
		npc.setNPCGlowColor(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final GlowColor value) {
		file.setGlowColor(value).save();
	}

	@Override
	public Optional<GlowColor> readFromFile(final NPCFile file) {
		return file.getGlowColor();
	}
}