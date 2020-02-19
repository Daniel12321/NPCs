package me.mrdaniel.npcs.catalogtypes.conditiontype;

import me.mrdaniel.npcs.actions.conditions.Condition;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.function.Function;

@CatalogedBy(ConditionTypes.class)
public class ConditionType implements CatalogType {

	private final String name;
	private final String id;
	private final Function<ConfigurationNode, Condition> condition;

	ConditionType(String name, String id, Function<ConfigurationNode, Condition> condition) {
		this.name = name;
		this.id = id;
		this.condition = condition;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	public Function<ConfigurationNode, Condition> getCondition() {
		return condition;
	}
}
