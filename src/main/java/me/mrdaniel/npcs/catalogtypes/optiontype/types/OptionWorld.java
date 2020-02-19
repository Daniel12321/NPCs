package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class OptionWorld extends OptionType<World> {

	public OptionWorld() {
		super("World", "world");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final World value) {
		npc.setNPCWorld(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final World value) {
		file.setWorld(value).save();
	}

	@Override
	public Optional<World> readFromFile(final NPCFile file) {
		return file.getWorld();
	}
}
