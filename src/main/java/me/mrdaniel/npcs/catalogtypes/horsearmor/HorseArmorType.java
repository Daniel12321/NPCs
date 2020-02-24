package me.mrdaniel.npcs.catalogtypes.horsearmor;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(HorseArmorTypes.class)
public class HorseArmorType implements CatalogType {

	private final String name;
	private final String id;
	private final ItemType type;

	HorseArmorType(String name, String id, ItemType type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public ItemType getType() {
		return type;
	}
}
