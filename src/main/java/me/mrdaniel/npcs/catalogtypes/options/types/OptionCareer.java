package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.Career;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityVillager;

public class OptionCareer extends OptionType<Career> {

	public OptionCareer() {
		super("Career", "career");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityVillager;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Career value) {
		npc.setNPCCareer(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Career value) {
		file.setCareer(value);
	}

	@Override
	public Optional<Career> readFromFile(final NPCFile file) {
		return file.getCareer();
	}
}