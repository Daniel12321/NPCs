package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import net.minecraft.entity.passive.EntityOcelot;

public class OptionCatType extends OptionType<CatType> {

	public OptionCatType() {
		super("CatType", "cattype");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof EntityOcelot;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final CatType value) {
		npc.setNPCCatType(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final CatType value) {
		file.setCatType(value).save();
	}

	@Override
	public Optional<CatType> readFromFile(final NPCFile file) {
		return file.getCatType();
	}
}