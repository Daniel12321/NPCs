package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;

public class OptionCareer extends OptionType<Career> {

	public OptionCareer() {
		super("Career", "career");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityVillager || npc instanceof EntityZombieVillager;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Career value) {
		npc.setNPCCareer(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Career value) {
		file.setCareer(value).save();
	}

	@Override
	public Optional<Career> readFromFile(final NPCFile file) {
		return file.getCareer();
	}
}