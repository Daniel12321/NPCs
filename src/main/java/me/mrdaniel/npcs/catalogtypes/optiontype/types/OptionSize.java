package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionSize extends OptionType<Integer> {

	public OptionSize() {
		super("Size", "size", GenericArguments.integer(Text.of("size")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Slime;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Integer value) {
		npc.setNPCSize(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Integer value) {
		file.setSize(value).save();
	}

	@Override
	public Optional<Integer> readFromFile(final NPCFile file) {
		return Optional.of(file.getSize());
	}
}
