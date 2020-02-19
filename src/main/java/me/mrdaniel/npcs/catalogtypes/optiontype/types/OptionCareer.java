package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.entity.living.monster.ZombieVillager;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionCareer extends OptionType<Career> {

	public OptionCareer() {
		super("Career", "career", GenericArguments.catalogedElement(Text.of("career"), Career.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Villager || npc instanceof ZombieVillager;
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
