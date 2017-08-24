package me.mrdaniel.npcs.catalogtypes.glowcolor;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.text.TextFormatting;

@RequiredArgsConstructor
@CatalogedBy(GlowColors.class)
public class GlowColor implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final TextFormatting color;
}