package me.mrdaniel.npcs.data.npc.actions;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.actions.actions.Action;

public class RandomActions extends ListActions {

	public RandomActions(@Nonnull final List<Action> actions) {
		super("random", actions, Maps.newHashMap());
	}

	@Override
	public void execute(final NPCs npcs, final Player p, final Living npc) {
		this.actions.get((int) (Math.random()*this.actions.size())).execute(npcs, p, npc);
	}
}