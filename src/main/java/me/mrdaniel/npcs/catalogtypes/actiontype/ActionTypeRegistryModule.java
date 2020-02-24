package me.mrdaniel.npcs.catalogtypes.actiontype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class ActionTypeRegistryModule implements CatalogRegistryModule<ActionType> {

	@Override
	public Optional<ActionType> getById(String id) {
		for (ActionType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<ActionType> getAll() {
		return ActionTypes.ALL;
	}
}
