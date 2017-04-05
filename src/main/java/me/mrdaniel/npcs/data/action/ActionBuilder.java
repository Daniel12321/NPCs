package me.mrdaniel.npcs.data.action;

import java.util.Optional;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ActionBuilder extends AbstractDataBuilder<Action> {

	public ActionBuilder() {
		super(Action.class, 1);
	}

	@Override
	protected Optional<Action> buildContent(DataView view) throws InvalidDataException {
		if (!view.contains(DataQuery.of("action_type")) || !view.contains(DataQuery.of("action_value"))) { return Optional.empty(); }
		return Optional.of(new Action(ActionType.of(view.getString(DataQuery.of("action_type")).get()), view.getString(DataQuery.of("action_value")).get()));
	}
}