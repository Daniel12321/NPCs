package me.mrdaniel.npcs.catalogtypes.career;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class CareerRegistryModule implements CatalogRegistryModule<Career> {

	@Getter private final List<Career> all;

	public CareerRegistryModule() {
		this.all = Lists.newArrayList(Careers.FARMER, Careers.FISHERMAN, Careers.SHEPHERD, Careers.FLETCHER, Careers.LIBRARIAN, Careers.CARTOGRAPHER, Careers.CLERIC, Careers.ARMORER, Careers.WEAPON_SMITH, Careers.TOOL_SMITH, Careers.BUTCHER, Careers.LEATHERWORKER, Careers.NITWIT);
	}

	@Override
	public Optional<Career> getById(@Nonnull final String id) {
		for (Career career : this.all) { if (career.getId().equalsIgnoreCase(id)) { return Optional.of(career); } }
		return Optional.empty();
	}
}