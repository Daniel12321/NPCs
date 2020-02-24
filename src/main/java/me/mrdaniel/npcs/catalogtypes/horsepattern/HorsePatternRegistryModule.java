package me.mrdaniel.npcs.catalogtypes.horsepattern;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class HorsePatternRegistryModule implements CatalogRegistryModule<HorsePattern> {

	@Override
	public Optional<HorsePattern> getById(String id) {
		for (HorsePattern type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<HorsePattern> getAll() {
		return HorsePatterns.ALL;
	}
}
