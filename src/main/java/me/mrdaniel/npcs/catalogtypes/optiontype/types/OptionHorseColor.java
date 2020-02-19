package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionHorseColor extends OptionType<HorseColor> {

	public OptionHorseColor() {
		super("HorseColor", "horsecolor", GenericArguments.catalogedElement(Text.of("horsecolor"), HorseColor.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Horse;
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
