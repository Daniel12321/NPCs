package me.mrdaniel.npcs.catalogtypes.color;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class ColorTypeRegistryModule implements CatalogRegistryModule<ColorType> {

	@Override
	public Optional<ColorType> getById(String id) {
		for (ColorType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<ColorType> getAll() {
		return ColorTypes.ALL;
	}
}
