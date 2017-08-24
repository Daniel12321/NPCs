package me.mrdaniel.npcs.catalogtypes.conditiontype;

import java.util.function.Function;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import me.mrdaniel.npcs.actions.conditions.Condition;
import ninja.leaping.configurate.ConfigurationNode;

@CatalogedBy(ConditionTypes.class)
public class ConditionType implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	@Getter private final Function<ConfigurationNode, Condition> condition;

	protected ConditionType(String name, String id, Function<ConfigurationNode, Condition> condition) {
		this.name = name;
		this.id = id;
		this.condition = condition;
	}
}