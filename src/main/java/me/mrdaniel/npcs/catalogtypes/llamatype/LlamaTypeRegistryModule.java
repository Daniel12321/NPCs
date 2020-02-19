package me.mrdaniel.npcs.catalogtypes.llamatype;

import org.spongepowered.api.registry.CatalogRegistryModule;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;

public class LlamaTypeRegistryModule implements CatalogRegistryModule<LlamaType> {

	@Override
	public Optional<LlamaType> getById(@Nonnull final String id) {
		for (LlamaType type : this.getAll()) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}

	@Override
	public Collection<LlamaType> getAll() {
		return LlamaTypes.ALL;
	}
}
