package me.mrdaniel.npcs.data.action;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.manager.NPCManager;

public class NPCIterateActions extends NPCActions {

	private Map<UUID, Integer> current;
	private boolean repeating;

	public NPCIterateActions(@Nonnull final List<Action> actions) { this(actions, Maps.newHashMap(), true); }
	public NPCIterateActions(@Nonnull final List<Action> actions, @Nonnull final Map<UUID, Integer> current, final boolean repeating) {
		super(actions);

		this.current = current;
		this.repeating = repeating;
	}

	public void setRepeating(final boolean repeating) { this.repeating = repeating; }

	@Override
	public DataContainer toContainer() {
		return super.toContainer()
				.set(DataQuery.of("current"), this.current)
				.set(DataQuery.of("repeating"), this.repeating);
	}

	@Override
	public void execute(@Nonnull final NPCs npcs, @Nonnull final Player p) {
		int current = Optional.ofNullable(this.current.get(p.getUniqueId())).orElse(0);

		if (this.actions.size() > current) {
			this.actions.get(current).execute(npcs, p);
			this.current.put(p.getUniqueId(), (current+1 >= this.actions.size() && this.repeating) ? 0 : current+1);
		}
	}

	@Override
	protected List<Text> getOptionLines() {
		return Lists.newArrayList(Text.builder().append(Text.of(TextColors.GOLD, "Repeating: ", NPCManager.getBooleanText(this.repeating))).onHover(TextActions.showText(NPCManager.getToggleText(this.repeating))).onClick(TextActions.runCommand("/npc repeating " + !this.repeating)).build());
	}
}