package me.mrdaniel.npcs.catalogtypes.menupagetype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;

@CatalogedBy(PageTypes.class)
public class PageType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;

	protected PageType(String name, String id) {
		this.name = name;
		this.id = id;
	}
}