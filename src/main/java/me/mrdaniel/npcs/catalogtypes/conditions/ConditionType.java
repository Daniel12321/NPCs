package me.mrdaniel.npcs.catalogtypes.conditions;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;

@CatalogedBy(ConditionTypes.class)
public class ConditionType implements CatalogType {

	private final String name;
	private final String id;
	private final Class<? extends Condition> condition; 

	protected ConditionType(String name, String id, Class<? extends Condition> condition) {
		this.name = name;
		this.id = id;
		this.condition = condition;
	}

	@Nonnull @Override public String getName() { return this.name; }
	@Nonnull @Override public String getId() { return this.id; }
	@Nonnull public Class<? extends Condition> getConditionClass() { return this.condition; }
}