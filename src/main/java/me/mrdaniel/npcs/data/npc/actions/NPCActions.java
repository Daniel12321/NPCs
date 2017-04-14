package me.mrdaniel.npcs.data.npc.actions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.npc.actions.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;
import me.mrdaniel.npcs.utils.TextUtils;

public abstract class NPCActions implements DataSerializable {

	private final String type;

	public NPCActions(@Nonnull final String type) {
		this.type = type;
	}

	@Nonnull
	public List<Text> getLines() {
		List<Action> actions = this.getActions();
		List<Text> lines = Lists.newArrayList(Text.of(TextColors.GOLD, "Actions: "));

		for (int i = 1; i <= actions.size(); i++) {
			Action a = actions.get(i-1);
			lines.add(Text.builder()
					.append(Text.builder().append(Text.of(TextColors.YELLOW, "[⬆]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Up"))).onClick(TextActions.runCommand("/npc swap " + i + " " + (i-1))).build())
					.append(Text.of(" "))
					.append(Text.builder().append(Text.of(TextColors.YELLOW, "[⬇]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Down"))).onClick(TextActions.runCommand("/npc swap " + i + " " + (i+1))).build())
					.append(Text.of(" "))
					.append(Text.builder().append(Text.of(TextColors.RED, "[x]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.runCommand("/npc removeaction " + i)).build())
					.append(Text.of(TextColors.GOLD, "  ", i, ": ", TextColors.DARK_GREEN, a.getType().getName(), " - "))
					.append(TextUtils.toText(a.getValue()))
					.build());
		}
		lines.add(Text.of("      "));

		if (this instanceof ConditionActions) {
			lines.add(Text.of(TextColors.GOLD, "Conditions: "));
			
		}

		lines.addAll(this.getOptionLines());

		return lines;
	}

	@Override public int getContentVersion() { return 2; }

	@Override
	public DataContainer toContainer() {
		return new MemoryDataContainer()
				.set(DataQuery.of("ContentVersion"), this.getContentVersion())
				.set(DataQuery.of("type"), this.type);
	}

	public abstract void execute(NPCs npcs, Player p, Living npc);

	public abstract List<Action> getActions();
	public abstract Map<UUID, Integer> getCurrent();
	public abstract List<Condition> getConditions();
	protected abstract List<Text> getOptionLines();
}