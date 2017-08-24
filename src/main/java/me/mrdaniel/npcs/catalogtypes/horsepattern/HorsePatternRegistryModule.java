package me.mrdaniel.npcs.catalogtypes.horsepattern;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class HorsePatternRegistryModule implements CatalogRegistryModule<HorsePattern> {

	@Getter private final List<HorsePattern> all;

	public HorsePatternRegistryModule() {
		this.all = Lists.newArrayList(HorsePatterns.NONE, HorsePatterns.WHITE, HorsePatterns.WHITE_FIELDS, HorsePatterns.WHITE_DOTS, HorsePatterns.BLACK_DOTS);
	}

	@Override
	public Optional<HorsePattern> getById(@Nonnull final String id) {
		for (HorsePattern type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}