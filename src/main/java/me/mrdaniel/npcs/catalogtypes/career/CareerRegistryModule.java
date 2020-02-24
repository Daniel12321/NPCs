package me.mrdaniel.npcs.catalogtypes.career;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class CareerRegistryModule implements CatalogRegistryModule<Career> {

	@Override
	public Optional<Career> getById(String id) {
		for (Career career : this.getAll()) {
			if (career.getId().equalsIgnoreCase(id)) {
				return Optional.of(career);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<Career> getAll() {
		return Careers.ALL;
	}
}
