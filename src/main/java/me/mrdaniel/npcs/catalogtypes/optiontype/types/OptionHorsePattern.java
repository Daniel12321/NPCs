package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityHorse;

public class OptionHorsePattern extends OptionType<HorsePattern> {

	public OptionHorsePattern() {
		super("HorsePattern", "horsepattern");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityHorse;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final HorsePattern value) {
		npc.setNPCHorsePattern(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final HorsePattern value) {
		file.setHorsePattern(value).save();
	}

	@Override
	public Optional<HorsePattern> readFromFile(final NPCFile file) {
		return file.getHorsePattern();
	}
}