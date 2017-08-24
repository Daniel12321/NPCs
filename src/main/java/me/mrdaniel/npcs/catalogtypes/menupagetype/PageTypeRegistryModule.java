package me.mrdaniel.npcs.catalogtypes.menupagetype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class PageTypeRegistryModule implements CatalogRegistryModule<PageType> {

	@Getter private final List<PageType> all;

	public PageTypeRegistryModule() {
		this.all = Lists.newArrayList(PageTypes.MAIN, PageTypes.ARMOR, PageTypes.ACTIONS);
	}

	@Override
	public Optional<PageType> getById(@Nonnull final String id) {
		for (PageType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}