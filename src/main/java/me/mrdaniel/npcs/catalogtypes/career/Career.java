package me.mrdaniel.npcs.catalogtypes.career;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CatalogedBy(Careers.class)
public class Career implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final int professionId;
	@Getter private final int careerId;
}