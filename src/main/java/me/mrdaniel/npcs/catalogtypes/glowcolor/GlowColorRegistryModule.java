package me.mrdaniel.npcs.catalogtypes.glowcolor;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class GlowColorRegistryModule implements CatalogRegistryModule<GlowColor> {

	@Override
	public Optional<GlowColor> getById(@Nonnull final String id) {
		for (GlowColor type : this.getAll()) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}

	@Override
	public Collection<GlowColor> getAll() {
		return GlowColors.ALL;
	}
}
