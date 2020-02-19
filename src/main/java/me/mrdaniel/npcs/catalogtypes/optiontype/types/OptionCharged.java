package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionCharged extends OptionType<Boolean> {

	public OptionCharged() {
		super("Charged", "charged", GenericArguments.bool(Text.of("charged")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Creeper;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCCharged(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setCharged(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getCharged());
	}
}
