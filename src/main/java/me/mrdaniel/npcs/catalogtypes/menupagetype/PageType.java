package me.mrdaniel.npcs.catalogtypes.menupagetype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(PageTypes.class)
public class PageType implements CatalogType {

	private final String name;
	private final String id;

	PageType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}
}
