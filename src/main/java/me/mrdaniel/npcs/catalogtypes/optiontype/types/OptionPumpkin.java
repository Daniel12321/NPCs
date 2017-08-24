package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.monster.EntitySnowman;

public class OptionPumpkin extends OptionType<Boolean> {

	public OptionPumpkin() {
		super("Pumpkin", "pumpkin");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntitySnowman;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCPumpkin(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setPumpkin(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getPumpkin());
	}
}