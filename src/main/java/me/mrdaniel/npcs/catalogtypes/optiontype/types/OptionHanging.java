package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.Bat;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionHanging extends OptionType<Boolean> {

	public OptionHanging() {
		super("Hanging", "hanging", GenericArguments.bool(Text.of("hanging")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Bat;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCHanging(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setHanging(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getHanging());
	}
}
