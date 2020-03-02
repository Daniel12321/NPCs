package me.mrdaniel.npcs.catalogtypes.aitype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(AITypes.class)
public class AIType implements CatalogType {

	private final String name;
	private final String id;

	protected AIType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
