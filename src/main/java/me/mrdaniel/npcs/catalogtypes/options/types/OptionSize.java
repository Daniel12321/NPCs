package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.monster.EntitySlime;

public class OptionSize extends OptionType<Integer> {

	public OptionSize() {
		super("Size", "size");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntitySlime;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Integer value) {
		npc.setNPCSize(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Integer value) {
		file.setSize(value);
	}

	@Override
	public Optional<Integer> readFromFile(final NPCFile file) {
		return Optional.of(file.getSize());
	}
}