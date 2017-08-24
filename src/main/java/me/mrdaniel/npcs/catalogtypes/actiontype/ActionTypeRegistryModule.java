package me.mrdaniel.npcs.catalogtypes.actiontype;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.registry.CatalogRegistryModule;

import com.google.common.collect.Lists;

import lombok.Getter;

public class ActionTypeRegistryModule implements CatalogRegistryModule<ActionType> {

	@Getter private final List<ActionType> all;

	public ActionTypeRegistryModule() {
		this.all = Lists.newArrayList(ActionTypes.MESSAGE, ActionTypes.CONSOLECMD, ActionTypes.PLAYERCMD, ActionTypes.CONDITION, ActionTypes.CHOICES, ActionTypes.PAUSE, ActionTypes.GOTO, ActionTypes.DELAY, ActionTypes.COOLDOWN);
	}

	@Override
	public Optional<ActionType> getById(@Nonnull final String id) {
		for (ActionType type : this.all) { if (type.getId().equalsIgnoreCase(id)) { return Optional.of(type); } }
		return Optional.empty();
	}
}