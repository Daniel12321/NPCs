package me.mrdaniel.npcs.catalogtypes.menupagetype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class PageTypeRegistryModule implements CatalogRegistryModule<PageType> {

	@Override
	public Optional<PageType> getById(@Nonnull final String id) {
		for (PageType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<PageType> getAll() {
		return PageTypes.ALL;
	}
}
