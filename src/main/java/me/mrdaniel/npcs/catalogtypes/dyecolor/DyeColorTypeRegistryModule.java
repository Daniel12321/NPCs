package me.mrdaniel.npcs.catalogtypes.dyecolor;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class DyeColorTypeRegistryModule implements CatalogRegistryModule<DyeColorType> {

	@Override
	public Optional<DyeColorType> getById(String id) {
		for (DyeColorType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<DyeColorType> getAll() {
		return DyeColorTypes.ALL;
	}
}
