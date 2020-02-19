package me.mrdaniel.npcs.catalogtypes.glowcolor;

import net.minecraft.util.text.TextFormatting;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(GlowColors.class)
public class GlowColor implements CatalogType {

	private final String name;
	private final String id;
	private final TextFormatting color;

	GlowColor(String name, String id, TextFormatting color) {
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

	public TextFormatting getColor() {
		return color;
	}
}
