package me.mrdaniel.npcs.catalogtypes.parrottype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class ParrotTypeRegistryModule implements CatalogRegistryModule<ParrotType> {

	@Getter private final List<ParrotType> all;

	public ParrotTypeRegistryModule() {
		this.all = Lists.newArrayList(ParrotTypes.RED, ParrotTypes.BLUE, ParrotTypes.GREEN, ParrotTypes.CYAN, ParrotTypes.SILVER);
	}

	@Override
	public Optional<ParrotType> getById(@Nonnull final String id) {
		for (ParrotType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}