package me.mrdaniel.npcs.catalogtypes.horsepattern;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(HorsePatterns.class)
public class HorsePattern implements CatalogType {

	private final String name;
	private final String id;
	private final int nbtId;

	HorsePattern(String name, String id, int nbtId) {
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
