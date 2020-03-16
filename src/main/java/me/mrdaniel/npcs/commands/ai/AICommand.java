package me.mrdaniel.npcs.commands.ai;

import me.mrdaniel.npcs.ai.pattern.AIPatternStay;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.AIChatMenu;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AICommand extends NPCCommand {

	private final List<AIType> types;

	public AICommand(List<AIType> types) {
		super(AIChatMenu::new, false);

		this.types = types;
	}

	@Override
	public void execute(Player p, INPCData data, @Nullable NPCAble npc, CommandContext args) throws CommandException {
		AbstractAIPattern ai = data.getProperty(PropertyTypes.AI_PATTERN).orElse(new AIPatternStay(data.getProperty(PropertyTypes.TYPE).get()));

		if (!this.types.contains(ai.getType())) {
			throw new CommandException(Text.of(TextColors.RED, "This command is not supported by the NPCs AI type!"));
		}

		if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		this.execute(p, ai, args);
		data.setProperty(PropertyTypes.AI_PATTERN, ai).save();

		if (npc != null) {
			npc.refreshAI();
		}
	}

	public abstract void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException;
}
