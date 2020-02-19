package me.mrdaniel.npcs.actions;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.ActionResult;
import me.mrdaniel.npcs.managers.PlaceholderManager;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Map;
import java.util.UUID;

public class ActionChoices extends Action {

	private final Map<String, Integer> choices;

	public ActionChoices(ConfigurationNode node) {
		super(ActionTypes.CHOICES);

		this.choices = Maps.newHashMap();
		node.getNode("Choices").getChildrenMap().forEach((key, value) -> this.choices.put((String) key, value.getInt(0)));
	}

	public ActionChoices(Map<String, Integer> choices) {
		super(ActionTypes.CHOICES);

		this.choices = choices;
	}

	public Map<String, Integer> getChoices() {
		return this.choices;
	}

	@Override
	public void execute(Player p, NPCFile file, ActionResult result) {
		ActionManager.getInstance().setChoosing(p.getUniqueId(), file);

		UUID uuid = p.getUniqueId();

		Text.Builder b = Text.builder().append(Text.of(" "));
		this.choices.forEach((txt, next) -> b.append(Text.builder().append(Text.of(TextColors.RED, TextStyles.UNDERLINE, txt)).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Choose"))).onClick(TextActions.executeCallback(src -> {
			try {
				ActionManager.getInstance().executeChoice(file, uuid, next);
			} catch (final NPCException exc) {}
		})).build(), Text.of("  ")));

		p.sendMessage(PlaceholderManager.getInstance().formatChoiceMessage(p, b.build()));
		result.setPerformNextAction(false);
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("Choices").setValue(this.choices);
	}

	@Override
	public Text getLine(int index) {
		Text.Builder b = Text.builder().append(Text.of(TextColors.GOLD, "Choices: "));
		this.choices.forEach((name, next) -> b.append(this.getChoiceText(index, name, next), Text.of(" ")));
		return b.append(
				Text.builder().append(Text.of(TextColors.DARK_GREEN, "[+]"))
				.onHover(TextActions.showText(Text.of(TextColors.DARK_GREEN, "Add Choice")))
				.onClick(TextActions.suggestCommand("/npc action edit " + index + " addchoice <name> <goto>"))
				.build()).build();
	}

	private Text getChoiceText(int index, String name, int next) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name), 
						Text.builder().append(Text.of(TextColors.AQUA, "➡", next))
						.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
						.onClick(TextActions.suggestCommand("/npc action edit " + index + " setchoice " + name + " <goto>")).build(),
						Text.of(" "),
						Text.builder()
						.append(Text.of(TextColors.RED, "[x]"))
						.onHover(TextActions.showText(Text.of(TextColors.RED, "Remove")))
						.onClick(TextActions.runCommand("/npc action edit " + index + " removechoice " + name))
						.build())
				.build();
	}
}
