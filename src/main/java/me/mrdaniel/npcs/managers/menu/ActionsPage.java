package me.mrdaniel.npcs.managers.menu;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.utils.TextUtils;

public class ActionsPage extends Page {

	private List<Text[]> pages;
	private int count;
	private int current;

	public ActionsPage(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		super(npc, file);
	}

	private void add(@Nonnull final Text txt) {
		int page = this.count / 18;
		while (this.pages.size() <= page) { this.addPage(); }
		this.pages.get(page)[this.count++ % 18] = txt;
	}

	private void addPage() {
		Text[] page = new Text[18];
		for (int i = 0; i < 18; i++) { page[i] = Text.EMPTY; }
		this.pages.add(page);
	}

	@Nonnull @Override public Text[] getLines() { return this.pages.get(this.current); }

	public void addCurrent(final int add) {
		this.current += add;
		if (this.current < 0) { this.current = 0; }
		else if (this.current >= this.pages.size()) { this.current = this.pages.size()-1; }
	}


	@Override
	public void update(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.pages = Lists.newArrayList();
		this.count = 0;
		this.current = 0;
		this.updatePage(npc, file);
	}

	@Override
	public void updatePage(final Living npc, final NPCFile file) {
		this.add(Text.of(TextColors.GOLD, "Actions: "));

		for (int i = 0; i < file.getActions().size(); i++) {
			Action a = file.getActions().get(i);
			this.add(Text.builder()
					.append(this.getRemoveText(i),
							Text.of(" "),
							this.getUpText(i),
							Text.of(" "),
							this.getDownText(i),
							Text.of(TextColors.BLUE, " ", String.valueOf(i), ": "),
							a.getLine(i))
					.build());
		}
		this.add(Text.EMPTY);

		this.add(Text.builder().append(
				Text.of(TextColors.DARK_GREEN, "Add: "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Message]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Message")))
				.onClick(TextActions.suggestCommand("/npc action add message <message...>")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Delay]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Delay")))
				.onClick(TextActions.suggestCommand("/npc action add delay <ticks>")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Pause]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Pause")))
				.onClick(TextActions.runCommand("/npc action add pause")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Goto]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Goto")))
				.onClick(TextActions.suggestCommand("/npc action add goto <goto>")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Choices]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Choices")))
				.onClick(TextActions.suggestCommand("/npc action add choices <first> <goto_first> <second> <goto_second>")).build())
				.build());

		this.add(Text.builder().append(Text.of(TextColors.DARK_GREEN, "Add Command: "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Player]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Player Command")))
				.onClick(TextActions.suggestCommand("/npc action add playercmd <command...>")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Console]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Console Command")))
				.onClick(TextActions.suggestCommand("/npc action add consolecmd <command...>")).build())
				.build());

		this.add(Text.builder().append(Text.of(TextColors.DARK_GREEN, "Add Condition: "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Item]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Item Condition")))
				.onClick(TextActions.suggestCommand("/npc action add condition item <type> <amount> [name]")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Level]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Level Condition")))
				.onClick(TextActions.suggestCommand("/npc action add condition level <level>")).build(),
				Text.of(" "),
				Text.builder().append(Text.of(TextColors.YELLOW, "[Exp]"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Exp Condition")))
				.onClick(TextActions.suggestCommand("/npc action add condition exp <exp>")).build())
				.build());

		this.add(Text.EMPTY);
		this.add(TextUtils.getToggleText("Repeat", "/npc action repeat", file.getRepeatActions()));
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