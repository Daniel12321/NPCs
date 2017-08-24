package me.mrdaniel.npcs.catalogtypes.actiontype;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import me.mrdaniel.npcs.actions.Action;
import ninja.leaping.configurate.ConfigurationNode;

@CatalogedBy(ActionTypes.class)
public class ActionType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final Function<ConfigurationNode, Action> action;

	protected ActionType(@Nonnull final String name, @Nonnull final  String id, @Nonnull final Function<ConfigurationNode, Action> action) {
		this.name = name;
		this.id = id;
		this.action = action;
	}
}