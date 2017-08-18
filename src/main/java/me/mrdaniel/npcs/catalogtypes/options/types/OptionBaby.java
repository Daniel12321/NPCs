package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityZombie;

public class OptionBaby extends OptionType<Boolean> {

	public OptionBaby() {
		super("Baby", "baby");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityZombie || npc instanceof EntityAgeable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCBaby(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setBaby(value);
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getBaby());
	}
}