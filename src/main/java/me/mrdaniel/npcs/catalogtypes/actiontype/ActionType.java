package me.mrdaniel.npcs.catalogtypes.actiontype;

import me.mrdaniel.npcs.actions.Action;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.function.Function;

@CatalogedBy(ActionTypes.class)
public class ActionType implements CatalogType {

	private final String name;
	private final String id;
	private final Function<ConfigurationNode, Action> action;

	ActionType(String name, String id, Function<ConfigurationNode, Action> action) {
		this.name = name;
		this.id = id;
		this.action = action;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public Function<ConfigurationNode, Action> getAction() {
		return action;
	}
}
