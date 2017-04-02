package me.mrdaniel.npcs.data.action;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.MMOKeys;

public class NPCCommandAction implements NPCAction {

	private List<String> commands;
	private boolean random;
	private int current;

	public NPCCommandAction(@Nonnull final List<String> commands, final boolean random, final int current) {
		this.commands = commands;
		this.random = random;
		this.current = current;
	}

	@Nonnull public List<String> getCommands() { return this.commands; }
	public void setCommands(@Nonnull final List<String> commands) { this.commands = commands; this.current = 0; }
	public void addCommand(@Nonnull final String command) { this.commands.add(command); }

	public void setRandom(final boolean random) { this.random = random; }
	public boolean isRandom() { return this.random; }

	@Override
	public void execute(@Nonnull final NPCs npcs, @Nonnull final Player p) {
		if (this.random) { npcs.getGame().getCommandManager().process(p, this.commands.get((int) (Math.random()*this.commands.size()))); }
		else {
			npcs.getGame().getCommandManager().process(p, this.commands.get(this.current));
			this.current++;
			if (this.current >= this.commands.size()) { this.current = 0; }
		}
	}

	@Override
	public DataContainer addData(final DataContainer container) {
		return container.set(MMOKeys.COMMANDS.getQuery(), this.commands).set(MMOKeys.CURRENT.getQuery(), this.current);
	}

	@SuppressWarnings("unchecked")
	public static NPCCommandAction build(final DataView view) {
		return new NPCCommandAction(
				view.getList(MMOKeys.COMMANDS.getQuery()).map(l -> (List<String>)l).orElse(Lists.newArrayList()),
				view.getBoolean(MMOKeys.RANDOM.getQuery()).orElse(false),
				view.getInt(MMOKeys.CURRENT.getQuery()).orElse(0));
	}
}