package me.mrdaniel.npcs.catalogtypes.horsecolor;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class HorseColorRegistryModule implements CatalogRegistryModule<HorseColor> {

	@Override
	public Optional<HorseColor> getById(String id) {
		for (HorseColor type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<HorseColor> getAll() {
		return HorseColors.ALL;
	}
}
