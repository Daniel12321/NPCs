package me.mrdaniel.npcs.data.npc.actions.conditions;

import java.util.Optional;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ConditionBuilder extends AbstractDataBuilder<Condition> {

	public ConditionBuilder() {
		super(Condition.class, 1);
	}

	@Override
	protected Optional<Condition> buildContent(DataView view) throws InvalidDataException {
		return Optional.of(new Condition(ConditionType.of(view.getString(DataQuery.of("condition_type")).get())));
	}
}