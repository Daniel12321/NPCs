package me.mrdaniel.npcs.catalogtypes.cattype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CatalogedBy(CatTypes.class)
public class CatType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final int nbtId;
}