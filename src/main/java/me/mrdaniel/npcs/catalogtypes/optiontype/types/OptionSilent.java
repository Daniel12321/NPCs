package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionSilent extends OptionType<Boolean> {

	public OptionSilent() {
		super("Silent", "silent", GenericArguments.bool(Text.of("silent")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCSilent(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setSilent(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getSilent());
	}
}
