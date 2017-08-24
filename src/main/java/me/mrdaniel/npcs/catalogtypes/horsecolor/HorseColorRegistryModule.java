package me.mrdaniel.npcs.catalogtypes.horsecolor;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class HorseColorRegistryModule implements CatalogRegistryModule<HorseColor> {

	@Getter private final List<HorseColor> all;

	public HorseColorRegistryModule() {
		this.all = Lists.newArrayList(HorseColors.WHITE, HorseColors.CREAMY, HorseColors.CHESTNUT, HorseColors.BROWN, HorseColors.BLACK, HorseColors.GRAY, HorseColors.DARK_BROWN);
	}

	@Override
	public Optional<HorseColor> getById(@Nonnull final String id) {
		for (HorseColor type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}