package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.data.type.OcelotType;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityOcelot;

public class OptionCatType extends OptionType<OcelotType> {

	public OptionCatType() {
		super("CatType", "cattype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityOcelot;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final OcelotType value) {
		npc.setNPCCatType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final OcelotType value) {
		file.setCatType(value);
	}

	@Override
	public Optional<OcelotType> readFromFile(final NPCFile file) {
		return file.getCatType();
	}
}