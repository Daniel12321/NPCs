package me.mrdaniel.npcs.catalogtypes.horsearmor;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import javax.annotation.Nullable;

@CatalogedBy(HorseArmorTypes.class)
public class HorseArmorType implements CatalogType {

	private final String name;
	private final String id;
	@Nullable private final ItemType type;

	HorseArmorType(String name, String id, @Nullable ItemType type) {
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

	@Nullable
	public ItemType getType() {
		return type;
	}
}
