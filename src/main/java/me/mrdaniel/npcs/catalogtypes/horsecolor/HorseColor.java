package me.mrdaniel.npcs.catalogtypes.horsecolor;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CatalogedBy(HorseColors.class)
public class HorseColor implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final int nbtId;
}