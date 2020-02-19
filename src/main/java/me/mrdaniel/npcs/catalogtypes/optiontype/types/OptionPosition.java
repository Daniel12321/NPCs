package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.utils.Position;

import java.util.Optional;

public class OptionPosition extends OptionType<Position> {

	public OptionPosition() {
		super("Position", "position");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Position value) {
		npc.setNPCPosition(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Position value) {
		file.setPosition(value).save();
	}

	@Override
	public Optional<Position> readFromFile(final NPCFile file) {
		return Optional.of(new Position(file.getX(), file.getY(), file.getZ(), file.getYaw(), file.getPitch()));
	}
}
