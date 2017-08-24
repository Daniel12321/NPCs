package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityRabbit;

public class OptionRabbitType extends OptionType<RabbitType> {

	public OptionRabbitType() {
		super("RabbitType", "rabbittype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityRabbit;
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