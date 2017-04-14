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

public abstract class ListActions extends NPCActions {

	protected final List<Action> actions;
	protected final Map<UUID, Integer> current;

	public ListActions(@Nonnull final String type, @Nonnull final List<Action> actions, @Nonnull final Map<UUID, Integer> current) {
		super(type);

		this.actions = actions;
		this.current = current;
	}

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(DataQuery.of("action_list"), this.actions)
				.set(DataQuery.of("current"), this.current);
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
		return Lists.newArrayList();
	}

	@Override
	protected List<Text> getOptionLines() {
		return Lists.newArrayList();
	}
}