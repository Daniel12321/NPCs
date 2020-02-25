package me.mrdaniel.npcs.catalogtypes.dyecolor;

import net.minecraft.item.EnumDyeColor;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(DyeColorTypes.class)
public class DyeColorType implements CatalogType {

	private final String name;
	private final String id;
	private final EnumDyeColor color;

	DyeColorType(String name, String id, EnumDyeColor color) {
		this.name = name;
		this.id = id;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public EnumDyeColor getColor() {
		return color;
	}
}
