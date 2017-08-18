package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.ParrotVariant;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityParrot;

public class OptionParrotType extends OptionType<ParrotVariant> {

	public OptionParrotType() {
		super("ParrotType", "parrottype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityParrot;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ParrotVariant value) {
		npc.setNPCParrotType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ParrotVariant value) {
		file.setParrotType(value);
	}

	@Override
	public Optional<ParrotVariant> readFromFile(final NPCFile file) {
		return file.getParrotType();
	}
}