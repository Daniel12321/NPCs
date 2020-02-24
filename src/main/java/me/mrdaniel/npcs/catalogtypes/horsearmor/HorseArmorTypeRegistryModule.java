package me.mrdaniel.npcs.catalogtypes.horsearmor;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class HorseArmorTypeRegistryModule implements CatalogRegistryModule<HorseArmorType> {

	@Override
	public Optional<HorseArmorType> getById(String id) {
		for (HorseArmorType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<HorseArmorType> getAll() {
		return HorseArmorTypes.ALL;
	}
}
