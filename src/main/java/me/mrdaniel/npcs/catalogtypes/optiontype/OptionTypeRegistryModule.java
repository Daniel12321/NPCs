package me.mrdaniel.npcs.catalogtypes.optiontype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class OptionTypeRegistryModule implements CatalogRegistryModule<OptionType> {

	@Override
	public Optional<OptionType> getById(@Nonnull final String id) {
		for (OptionType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<OptionType> getAll() {
		return OptionTypes.ALL;
	}
}
