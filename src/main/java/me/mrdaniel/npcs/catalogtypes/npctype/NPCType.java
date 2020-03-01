package me.mrdaniel.npcs.catalogtypes.npctype;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(NPCTypes.class)
public class NPCType implements CatalogType {

	private final String name;
	private final String id;
	private final EntityType entityType;
	private final double movementSpeed;
	private final boolean ageable;
	private final boolean armorEquipable;

	NPCType(String name, String id, EntityType entityType, double movementSpeed) {
		this(name, id, entityType, movementSpeed, false, false);
	}

	NPCType(String name, String id, EntityType entityType, double movementSpeed, boolean ageable) {
		this(name, id, entityType, movementSpeed, ageable, false);
	}

	NPCType(String name, String id, EntityType entityType, double movementSpeed, boolean ageable, boolean armorEquipable) {
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.movementSpeed = movementSpeed;
		this.armorEquipable = armorEquipable;
		this.ageable = ageable;
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

	public double getMovementSpeed() {
		return movementSpeed;
	}

	public boolean isAgeable() {
		return ageable;
	}

	public boolean isArmorEquipable() {
		return armorEquipable;
	}
}
