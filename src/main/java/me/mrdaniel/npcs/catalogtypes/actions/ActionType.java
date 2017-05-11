package me.mrdaniel.npcs.catalogtypes.actions;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import me.mrdaniel.npcs.data.npc.actions.Action;

@CatalogedBy(ActionTypes.class)
public class ActionType implements CatalogType {

	private final String name;
	private final String id;
	private final Class<? extends Action> action;

	protected ActionType(String name, String id, Class<? extends Action> action) {
		this.name = name;
		this.id = id;
		this.action = action;
	}

	@Nonnull @Override public String getName() { return this.name; }
	@Nonnull @Override public String getId() { return this.id; }
	@Nonnull public Class<? extends Action> getActionClass() {return this.action; }
}