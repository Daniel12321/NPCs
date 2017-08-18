package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.HorseStyle;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityHorse;

public class OptionHorseStyle extends OptionType<HorseStyle> {

	public OptionHorseStyle() {
		super("HorseStyle", "horsestyle");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityHorse;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final HorseStyle value) {
		npc.setNPCHorseStyle(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final HorseStyle value) {
		file.setHorseStyle(value);
	}

	@Override
	public Optional<HorseStyle> readFromFile(final NPCFile file) {
		return file.getHorseStyle();
	}
}