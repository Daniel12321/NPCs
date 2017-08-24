package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityPig;

public class OptionSaddle extends OptionType<Boolean> {

	public OptionSaddle() {
		super("Saddle", "saddle");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityPig;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCSaddle(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setSaddle(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getSaddle());
	}
}