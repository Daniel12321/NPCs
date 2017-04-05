package me.mrdaniel.npcs.data.action;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class NPCActionsBuilder extends AbstractDataBuilder<NPCActions> {

	public NPCActionsBuilder() {
		super(NPCActions.class, 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<NPCActions> buildContent(DataView view) throws InvalidDataException {
		List<Action> actions = view.getSerializableList(DataQuery.of("action_list"), Action.class).orElse(Lists.newArrayList());

		if (view.contains(DataQuery.of("current"))) { return Optional.of(new NPCIterateActions(actions, view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()), view.getBoolean(DataQuery.of("repeating")).orElse(false))); }
		else return Optional.of(new NPCRandomActions(actions));
	}
}