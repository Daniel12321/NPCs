package me.mrdaniel.npcs.catalogtypes.conditions;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

public class ConditionTypeRegistryModule implements CatalogRegistryModule<ConditionType> {

	@Override
	public Optional<ConditionType> getById(@Nonnull final String id) {
		return ConditionTypes.of(id);
	}

	@Override
	public Collection<ConditionType> getAll() {
		return ConditionTypes.VALUES;
	}
}