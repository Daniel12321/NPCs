package me.mrdaniel.npcs.catalogtypes.cattype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class CatTypeRegistryModule implements CatalogRegistryModule<CatType> {

	@Override
	public Optional<CatType> getById(String id) {
		for (CatType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<CatType> getAll() {
		return CatTypes.ALL;
	}
}
