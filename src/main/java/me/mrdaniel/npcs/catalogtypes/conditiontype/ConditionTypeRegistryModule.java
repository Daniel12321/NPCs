package me.mrdaniel.npcs.catalogtypes.conditiontype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class ConditionTypeRegistryModule implements CatalogRegistryModule<ConditionType> {

	@Getter private final List<ConditionType> all;

	public ConditionTypeRegistryModule() {
		this.all = Lists.newArrayList(ConditionTypes.ITEM, ConditionTypes.MONEY, ConditionTypes.LEVEL, ConditionTypes.EXP);
	}

	@Override
	public Optional<ConditionType> getById(@Nonnull final String id) {
		for (ConditionType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}