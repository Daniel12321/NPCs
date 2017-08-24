package me.mrdaniel.npcs.catalogtypes.cattype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class CatTypeRegistryModule implements CatalogRegistryModule<CatType> {

	@Getter private final List<CatType> all;

	public CatTypeRegistryModule() {
		this.all = Lists.newArrayList(CatTypes.WILD, CatTypes.TUXEDO, CatTypes.TABBY, CatTypes.SIAMESE);
	}

	@Override
	public Optional<CatType> getById(@Nonnull final String id) {
		for (CatType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}