package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.HorseColor;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
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
		file.setHorseColor(value);
	}

	@Override
	public Optional<HorseColor> readFromFile(final NPCFile file) {
		return file.getHorseColor();
	}
}