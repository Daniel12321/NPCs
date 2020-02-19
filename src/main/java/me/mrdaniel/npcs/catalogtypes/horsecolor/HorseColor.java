package me.mrdaniel.npcs.catalogtypes.horsecolor;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(HorseColors.class)
public class HorseColor implements CatalogType {

	private final String name;
	private final String id;
	private final int nbtId;

	HorseColor(String name, String id, int nbtId) {
		this.name = name;
		this.id = id;
		this.nbtId = nbtId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public int getNbtId() {
		return nbtId;
	}
}
