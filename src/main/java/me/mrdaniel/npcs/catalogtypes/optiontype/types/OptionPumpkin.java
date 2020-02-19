package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.golem.SnowGolem;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionPumpkin extends OptionType<Boolean> {

	public OptionPumpkin() {
		super("Pumpkin", "pumpkin", GenericArguments.bool(Text.of("pumpkin")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof SnowGolem;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCPumpkin(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setPumpkin(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getPumpkin());
	}
}
