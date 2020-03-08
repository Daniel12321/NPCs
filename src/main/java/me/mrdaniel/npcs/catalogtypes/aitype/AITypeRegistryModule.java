package me.mrdaniel.npcs.catalogtypes.aitype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class AITypeRegistryModule implements CatalogRegistryModule<AIType> {

	@Override
	public Optional<AIType> getById(String id) {
		for (AIType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<AIType> getAll() {
		return AITypes.ALL;
	}
}
