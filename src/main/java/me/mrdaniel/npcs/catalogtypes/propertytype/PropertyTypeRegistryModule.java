package me.mrdaniel.npcs.catalogtypes.propertytype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class PropertyTypeRegistryModule implements CatalogRegistryModule<PropertyType> {

	@Override
	public Optional<PropertyType> getById(String id) {
		for (PropertyType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<PropertyType> getAll() {
		return PropertyTypes.ALL;
	}
}
