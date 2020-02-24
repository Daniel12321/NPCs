package me.mrdaniel.npcs.catalogtypes.npctype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(NPCTypes.class)
public class NPCType implements CatalogType {

	private final String name;
	private final String id;
	private final EntityType entityType;
	private final boolean ageable;
	private final boolean armorEquipable;
	private final boolean tameable;

	NPCType(String name, String id, EntityType entityType) {
		this(name, id, entityType, false, false, false);
	}

	NPCType(String name, String id, EntityType entityType, boolean ageable) {
		this(name, id, entityType, ageable, false, false);
	}

	NPCType(String name, String id, EntityType entityType, boolean ageable, boolean armorEquipable) {
		this(name, id, entityType, ageable, armorEquipable, false);
	}

	NPCType(String name, String id, EntityType entityType, boolean ageable, boolean armorEquipable, boolean tameable) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.armorEquipable = armorEquipable;
		this.ageable = ageable;
		this.tameable = tameable;
	}

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

	public boolean isAgeable() {
		return ageable;
	}

	public boolean isArmorEquipable() {
		return armorEquipable;
	}

	public boolean isTameable() {
		return tameable;
	}
}
