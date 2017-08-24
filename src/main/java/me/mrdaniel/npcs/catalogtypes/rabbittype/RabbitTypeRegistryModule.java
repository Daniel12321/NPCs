package me.mrdaniel.npcs.catalogtypes.rabbittype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class RabbitTypeRegistryModule implements CatalogRegistryModule<RabbitType> {

	@Getter private final List<RabbitType> all;

	public RabbitTypeRegistryModule() {
		this.all = Lists.newArrayList(RabbitTypes.BROWN, RabbitTypes.WHITE, RabbitTypes.BLACK, RabbitTypes.BLACK_AND_WHITE, RabbitTypes.GOLD, RabbitTypes.SALT_AND_PEPPER, RabbitTypes.KILLER);
	}

	@Override
	public Optional<RabbitType> getById(@Nonnull final String id) {
		for (RabbitType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}