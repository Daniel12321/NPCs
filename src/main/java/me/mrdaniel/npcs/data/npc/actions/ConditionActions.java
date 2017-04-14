package me.mrdaniel.npcs.data.npc.actions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.data.npc.actions.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;

public abstract class ConditionActions extends NPCActions {

	protected final List<Action> actions;
	protected final List<Action> completeactions;

	protected final Map<UUID, Integer> current;
	protected final List<Condition> conditions;
	protected final List<UUID> completed;

	public ConditionActions(@Nonnull final String type, @Nonnull final List<Action> actions, @Nonnull final List<Action> completeactions, @Nonnull final Map<UUID, Integer> current, @Nonnull final List<Condition> conditions, @Nonnull final List<UUID> completed) {
		super(type);

		this.actions = actions;
		this.completeactions = completeactions;

		this.current = current;
		this.conditions = conditions;
		this.completed = completed;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(DataQuery.of("action_list"), this.actions)
				.set(DataQuery.of("complete_action_list"), this.completeactions)
				.set(DataQuery.of("current"), this.current)
				.set(DataQuery.of("conditions"), this.conditions)
				.set(DataQuery.of("completed"), this.completed);
	}

	@Override
	public List<Action> getActions() {
		return this.actions;
	}

	@Override
	public Map<UUID, Integer> getCurrent() {
		return this.current;
	}

	@Override
	public List<Condition> getConditions() {
		return this.conditions;
	}

	@Override
	protected List<Text> getOptionLines() {
		return Lists.newArrayList();
	}
}