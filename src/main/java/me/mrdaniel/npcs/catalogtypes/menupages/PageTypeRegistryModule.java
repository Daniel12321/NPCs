package me.mrdaniel.npcs.catalogtypes.menupages;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class PageTypeRegistryModule implements CatalogRegistryModule<PageType> {

	@Getter private static PageTypeRegistryModule instance = new PageTypeRegistryModule();

	@Getter private final List<PageType> all;

	private PageTypeRegistryModule() {
		this.all = Lists.newArrayList(PageTypes.MAIN, PageTypes.ARMOR, PageTypes.ACTIONS);
	}

	@Override
	public Optional<PageType> getById(@Nonnull final String id) {
		for (PageType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}