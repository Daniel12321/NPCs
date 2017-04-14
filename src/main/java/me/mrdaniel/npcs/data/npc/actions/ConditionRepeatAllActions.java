package me.mrdaniel.npcs.data.npc.actions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.actions.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;

public class ConditionRepeatAllActions extends ConditionActions {

	public ConditionRepeatAllActions(@Nonnull final List<Action> actions, @Nonnull final List<Action> completeactions, @Nonnull final Map<UUID, Integer> current, @Nonnull final List<Condition> conditions, @Nonnull final List<UUID> completed) {
		super("condition_repeat_all", actions, completeactions, current, conditions, completed);
	}

	@Override
	public void execute(final NPCs npcs, final Player p, final Living npc) {
		boolean done = super.completed.contains(p.getUniqueId());
		int current = Optional.ofNullable(super.current.get(p.getUniqueId())).orElse(0);

		List<Action> actions = done ? super.completeactions : super.actions;

		if (current < super.current.size()) {
			actions.get(current).execute(npcs, p, npc);
			super.current.put(p.getUniqueId(), current+1);
		}
		else {
			super.current.put(p.getUniqueId(), 0);
			if (!done && super.conditions.stream().allMatch(condition -> condition.isMet(p))) {
				super.conditions.forEach(condition -> condition.apply());
				super.completed.add(p.getUniqueId());
				this.execute(npcs, p, npc);
			}
		}
	}
}