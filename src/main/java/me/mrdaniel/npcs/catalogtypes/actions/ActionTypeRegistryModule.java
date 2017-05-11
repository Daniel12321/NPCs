package me.mrdaniel.npcs.catalogtypes.actions;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

public class ActionTypeRegistryModule implements CatalogRegistryModule<ActionType> {

	@Override
	public Optional<ActionType> getById(@Nonnull final String id) {
		return ActionTypes.of(id);
	}

	@Override
	public Collection<ActionType> getAll() {
		return ActionTypes.VALUES;
	}
}