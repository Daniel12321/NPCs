package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Ageable;
import org.spongepowered.api.entity.living.monster.Zombie;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionBaby extends OptionType<Boolean> {

	public OptionBaby() {
		super("Baby", "baby", GenericArguments.bool(Text.of("baby")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Zombie || npc instanceof Ageable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCBaby(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setBaby(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getBaby());
	}
}
