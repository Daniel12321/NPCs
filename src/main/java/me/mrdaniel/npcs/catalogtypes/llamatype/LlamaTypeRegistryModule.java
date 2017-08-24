package me.mrdaniel.npcs.catalogtypes.llamatype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class LlamaTypeRegistryModule implements CatalogRegistryModule<LlamaType> {

	@Getter private final List<LlamaType> all;

	public LlamaTypeRegistryModule() {
		this.all = Lists.newArrayList(LlamaTypes.CREAMY, LlamaTypes.WHITE, LlamaTypes.BROWN, LlamaTypes.GRAY);
	}

	@Override
	public Optional<LlamaType> getById(@Nonnull final String id) {
		for (LlamaType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}