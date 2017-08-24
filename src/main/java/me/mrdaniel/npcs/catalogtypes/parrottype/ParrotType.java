package me.mrdaniel.npcs.catalogtypes.parrottype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CatalogedBy(ParrotTypes.class)
public class ParrotType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final int nbtId;
}