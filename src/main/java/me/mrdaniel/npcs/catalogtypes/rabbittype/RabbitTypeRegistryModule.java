package me.mrdaniel.npcs.catalogtypes.rabbittype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class RabbitTypeRegistryModule implements CatalogRegistryModule<RabbitType> {

	@Override
	public Optional<RabbitType> getById(@Nonnull final String id) {
		for (RabbitType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<RabbitType> getAll() {
		return RabbitTypes.ALL;
	}
}
