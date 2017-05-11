package me.mrdaniel.npcs.catalogtypes.menupages;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(PageTypes.class)
public class PageType implements CatalogType {

	private final String name;
	private final String id;

	protected PageType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	@Nonnull @Override public String getName() { return this.name; }
	@Nonnull @Override public String getId() { return this.id; }
}