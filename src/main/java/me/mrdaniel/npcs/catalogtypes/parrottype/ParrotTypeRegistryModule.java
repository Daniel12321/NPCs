package me.mrdaniel.npcs.catalogtypes.parrottype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class ParrotTypeRegistryModule implements CatalogRegistryModule<ParrotType> {

	@Override
	public Optional<ParrotType> getById(String id) {
		for (ParrotType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<ParrotType> getAll() {
		return ParrotTypes.ALL;
	}
}
