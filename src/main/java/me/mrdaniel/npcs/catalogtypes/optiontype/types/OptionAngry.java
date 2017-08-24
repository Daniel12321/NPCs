package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityWolf;

public class OptionAngry extends OptionType<Boolean> {

	public OptionAngry() {
		super("Angry", "Angry");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityWolf;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCAngry(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setAngry(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getAngry());
	}
}