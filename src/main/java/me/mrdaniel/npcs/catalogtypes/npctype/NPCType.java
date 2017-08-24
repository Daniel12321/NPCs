package me.mrdaniel.npcs.catalogtypes.npctype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CatalogedBy(NPCTypes.class)
public class NPCType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final EntityType entityType;
}