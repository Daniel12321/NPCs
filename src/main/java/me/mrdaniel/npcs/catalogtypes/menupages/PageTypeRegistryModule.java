package me.mrdaniel.npcs.catalogtypes.menupages;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

public class PageTypeRegistryModule implements CatalogRegistryModule<PageType> {

	@Override
	public Optional<PageType> getById(@Nonnull final String id) {
		return PageTypes.of(id);
	}

	@Override
	public Collection<PageType> getAll() {
		return PageTypes.VALUES;
	}
}