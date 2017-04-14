package me.mrdaniel.npcs.data.npc.actions;

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

import me.mrdaniel.npcs.data.npc.actions.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;

public class NPCActionsBuilder extends AbstractDataBuilder<NPCActions> {

	public NPCActionsBuilder() {
		super(NPCActions.class, 2);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<NPCActions> buildContent(DataView view) throws InvalidDataException {
		String type = view.getString(DataQuery.of("type")).orElseThrow(() -> new InvalidDataException("Failed to find Action Mode."));

		List<Action> actions = view.getSerializableList(DataQuery.of("action_list"), Action.class).orElse(Lists.newArrayList());

		if (type.equalsIgnoreCase("random")) { return Optional.of(new RandomActions(actions)); }
		if (type.equalsIgnoreCase("iterate")) { return Optional.of(new IterateActions(actions, view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()))); }
		if (type.equalsIgnoreCase("only_once")) { return Optional.of(new OnlyOnceActions(actions, view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()))); }
		if (type.equalsIgnoreCase("only_once_repeat_last")) { return Optional.of(new OnlyOnceRepeatLastActions(actions, view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()))); }
		if (type.equalsIgnoreCase("condition_repeat_last")) { return Optional.of(new ConditionRepeatLastActions(actions, view.getSerializableList(DataQuery.of("complete_action_list"), Action.class).orElse(Lists.newArrayList()), view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()), view.getSerializableList(DataQuery.of("condition_list"), Condition.class).orElse(Lists.newArrayList()), view.getObjectList(DataQuery.of("completed"), UUID.class).orElse(Lists.newArrayList()))); }
		if (type.equalsIgnoreCase("condition_repeat_all")) { return Optional.of(new ConditionRepeatAllActions(actions, view.getSerializableList(DataQuery.of("complete_action_list"), Action.class).orElse(Lists.newArrayList()), view.getMap(DataQuery.of("current")).map(m -> (Map<UUID, Integer>)m).orElse(Maps.newHashMap()), view.getSerializableList(DataQuery.of("condition_list"), Condition.class).orElse(Lists.newArrayList()), view.getObjectList(DataQuery.of("completed"), UUID.class).orElse(Lists.newArrayList()))); }

		throw new InvalidDataException("Invalid Action Mode.");
	}
}