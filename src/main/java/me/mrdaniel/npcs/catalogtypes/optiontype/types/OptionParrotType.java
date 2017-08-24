package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityParrot;

public class OptionParrotType extends OptionType<ParrotType> {

	public OptionParrotType() {
		super("ParrotType", "parrottype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityParrot;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ParrotType value) {
		npc.setNPCParrotType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ParrotType value) {
		file.setParrotType(value).save();
	}

	@Override
	public Optional<ParrotType> readFromFile(final NPCFile file) {
		return file.getParrotType();
	}
}