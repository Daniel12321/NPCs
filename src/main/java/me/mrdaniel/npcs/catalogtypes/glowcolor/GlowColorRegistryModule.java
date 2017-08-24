package me.mrdaniel.npcs.catalogtypes.glowcolor;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class GlowColorRegistryModule implements CatalogRegistryModule<GlowColor> {

	@Getter private final List<GlowColor> all;

	public GlowColorRegistryModule() {
		this.all = Lists.newArrayList(GlowColors.BLACK, GlowColors.DARK_BLUE, GlowColors.DARK_GREEN, GlowColors.DARK_AQUA, GlowColors.DARK_RED, GlowColors.DARK_PURPLE, GlowColors.GOLD, GlowColors.GRAY, GlowColors.DARK_GRAY, GlowColors.BLUE, GlowColors.GREEN, GlowColors.AQUA, GlowColors.RED, GlowColors.LIGHT_PURPLE, GlowColors.YELLOW, GlowColors.WHITE);
	}

	@Override
	public Optional<GlowColor> getById(@Nonnull final String id) {
		for (GlowColor type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}