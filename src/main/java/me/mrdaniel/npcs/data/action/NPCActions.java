package me.mrdaniel.npcs.data.action;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.TextUtils;

public abstract class NPCActions implements DataSerializable {

	protected final List<Action> actions;

	public NPCActions(@Nonnull final List<Action> actions) {
		this.actions = Lists.newArrayList(actions);
	}

	@Override public int getContentVersion() { return 1; }

	@Override
	public DataContainer toContainer() {
		return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion()).set(DataQuery.of("action_list"), this.actions);
	}

	@Nonnull
	public List<Text> getLines() {
		List<Text> lines = Lists.newArrayList();
		for (int i = 1; i <= this.actions.size(); i++) {
			Action a = this.actions.get(i-1);
			lines.add(Text.builder()
					.append(Text.builder().append(Text.of(TextColors.YELLOW, "[⬆]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Up"))).onClick(TextActions.runCommand("/npc swap " + i + " " + (i-1))).build())
					.append(Text.of(" "))
					.append(Text.builder().append(Text.of(TextColors.YELLOW, "[⬇]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Down"))).onClick(TextActions.runCommand("/npc swap " + i + " " + (i+1))).build())
					.append(Text.of(" "))
					.append(Text.builder().append(Text.of(TextColors.RED, "[x]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.runCommand("/npc removeaction " + i)).build())
					.append(Text.of(TextColors.GOLD, "  ", i, ": ", TextColors.DARK_GREEN, a.getType().getName(), " - "))
					.append(TextUtils.toText(a.getValue()))
					.build());

//			lines.add(Text.builder().append(Text.of(TextColors.GOLD, "  ", i, ": ", TextColors.DARK_GREEN, a.getType().getName(), " - ", a.getValue(), "    ")).append(Text.builder().append(Text.of(TextColors.RED, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.runCommand("/npc removeaction " + i)).build()).build());
		}
		lines.add(Text.of("      "));
		lines.addAll(this.getOptionLines());

		return lines;
	}

	@Nonnull public List<Action> getActions() { return this.actions; }
	public void add(@Nonnull final Action action) { this.actions.add(action); }
	public void remove(final int number) { this.actions.remove(number-1); }
	public void swap(final int first, final int second) throws CommandException {
		if (first < 1 || second < 1 || this.actions.size() < first || this.actions.size() < second) { throw new CommandException(Text.of(TextColors.RED, "Invalid Action: No action with that number exists.")); }

		Action temp = this.actions.get(first-1);
		this.actions.set(first-1, this.actions.get(second-1));
		this.actions.set(second-1, temp);
	}

	public abstract void execute(NPCs npcs, Player p, Living npc);
	protected abstract List<Text> getOptionLines();
}