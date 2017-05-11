package me.mrdaniel.npcs.managers.menu;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.utils.TextUtils;

public class ActionsPage extends Page {

	public ActionsPage(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		super(npc, file);
	}

	@Override
	public void updatePage(final Living npc, final NPCFile file) {
		int c = 0;

		lines[c] = TextUtils.getToggleText("Repeat", "/npc action repeat", file.getRepeatActions());
		++c;

		lines[++c] = Text.of(TextColors.AQUA, "Actions: ");

		for (int i = 0; i < file.getActions().size(); i++) {
			Action a = file.getActions().get(i);
			lines[++c] = Text.builder()
					.append(this.getRemoveText(i),
							Text.of(" "),
							this.getUpText(i),
							Text.of(" "),
							this.getDownText(i),
							Text.of(TextColors.GOLD, " ", String.valueOf(i), ": "),
							a.getLine(i))
					.build();
		}
	}

	@Nonnull
	private Text getRemoveText(final int index) {
		return Text.builder()
				.append(Text.of(TextColors.RED, "[x]"))
				.onHover(TextActions.showText(Text.of(TextColors.RED, "Remove")))
				.onClick(TextActions.runCommand("/npc action remove " + index))
				.build();
	}

	@Nonnull
	private final Text getUpText(final int index) {
		return Text.builder()
				.append(Text.of(TextColors.YELLOW, "[⬆]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Up")))
				.onClick(TextActions.runCommand("/npc action swap " + (index - 1) + " " + index))
				.build();
	}

	@Nonnull
	private final Text getDownText(final int index) {
		return Text.builder()
				.append(Text.of(TextColors.YELLOW, "[⬇]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move Down")))
				.onClick(TextActions.runCommand("/npc action swap " + index + " " + (index + 1)))
				.build();
	}
}