package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Rabbit;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionRabbitType extends OptionType<RabbitType> {

	public OptionRabbitType() {
		super("RabbitType", "rabbittype", GenericArguments.catalogedElement(Text.of("rabbittype"), RabbitType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Rabbit;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final RabbitType value) {
		npc.setNPCRabbitType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final RabbitType value) {
		file.setRabbitType(value).save();
	}

	@Override
	public Optional<RabbitType> readFromFile(final NPCFile file) {
		return file.getRabbitType();
	}
}
