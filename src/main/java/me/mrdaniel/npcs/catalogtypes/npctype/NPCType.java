package me.mrdaniel.npcs.catalogtypes.npctype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(NPCTypes.class)
public class NPCType implements CatalogType {

	NPCType(String name, String id, EntityType entityType) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
	}

	private final String name;
	private final String id;
	private final EntityType entityType;

    @Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public EntityType getEntityType() {
		return entityType;
	}
}
