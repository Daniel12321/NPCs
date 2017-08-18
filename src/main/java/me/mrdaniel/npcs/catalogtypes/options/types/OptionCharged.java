package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.monster.EntityCreeper;

public class OptionCharged extends OptionType<Boolean> {

	public OptionCharged() {
		super("Charged", "charged");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityCreeper;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCCharged(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setCharged(value);
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getCharged());
	}
}