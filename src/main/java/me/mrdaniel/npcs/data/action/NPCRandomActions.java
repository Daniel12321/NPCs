package me.mrdaniel.npcs.data.action;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;

public class NPCRandomActions extends NPCActions {

	public NPCRandomActions(@Nonnull final List<Action> actions) {
		super(actions);
	}

	@Override
	public void execute(@Nonnull final NPCs npcs, @Nonnull final Player p) {
		if (!this.actions.isEmpty()) { this.actions.get((int) (Math.random()*this.actions.size())).execute(npcs, p); }
	}

	@Override
	protected List<Text> getOptionLines() {
		return Lists.newArrayList();
	}
}