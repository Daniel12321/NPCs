package me.mrdaniel.npcs.catalogtypes.npctype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.Optional;

public class NPCTypeRegistryModule implements CatalogRegistryModule<NPCType> {

	@Override
	public Optional<NPCType> getById(String id) {
		for (NPCType type : this.getAll()) {
			if (type.getId().equalsIgnoreCase(id)) {
				return Optional.of(type);
			}
		}
		return Optional.empty();
	}

	@Override
	public Collection<NPCType> getAll() {
		return NPCTypes.ALL;
	}
}
