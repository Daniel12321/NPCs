package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.animal.Ocelot;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class OptionCatType extends OptionType<CatType> {

	public OptionCatType() {
		super("CatType", "cattype", GenericArguments.catalogedElement(Text.of("cattype"), CatType.class));
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof Ocelot;
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
