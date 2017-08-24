package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityHorse;

public class OptionHorseColor extends OptionType<HorseColor> {

	public OptionHorseColor() {
		super("HorseColor", "horsecolor");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityHorse;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final HorseColor value) {
		npc.setNPCHorseColor(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final HorseColor value) {
		file.setHorseColor(value).save();
	}

	@Override
	public Optional<HorseColor> readFromFile(final NPCFile file) {
		return file.getHorseColor();
	}
}