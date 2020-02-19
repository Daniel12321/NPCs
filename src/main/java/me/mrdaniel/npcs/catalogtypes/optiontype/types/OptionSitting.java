package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityTameable;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionSitting extends OptionType<Boolean> {

	public OptionSitting() {
		super("Sitting", "sitting", GenericArguments.bool(Text.of("sitting")));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityTameable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final Boolean value) {
		npc.setNPCSitting(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final Boolean value) {
		file.setSitting(value).save();
	}

	@Override
	public Optional<Boolean> readFromFile(final NPCFile file) {
		return Optional.of(file.getSitting());
	}
}
