package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Parrot;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionParrotType extends OptionType<ParrotType> {

	public OptionParrotType() {
		super("ParrotType", "parrottype", GenericArguments.catalogedElement(Text.of("parrottype"), ParrotType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Parrot;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ParrotType value) {
		npc.setNPCParrotType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ParrotType value) {
		file.setParrotType(value).save();
	}

	@Override
	public Optional<ParrotType> readFromFile(final NPCFile file) {
		return file.getParrotType();
	}
}
