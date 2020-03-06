package me.mrdaniel.npcs.commands.ai;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.ai.pattern.AbstractAIGuardPattern;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypes;
import me.mrdaniel.npcs.utils.Position;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class CommandPositionSwap extends AICommand {

	public CommandPositionSwap() {
		super(Lists.newArrayList(AITypes.GUARD_PATROL, AITypes.GUARD_RANDOM));
	}

	@Override
	public void execute(Player p, AbstractAIPattern ai, CommandContext args) throws CommandException {
		AbstractAIGuardPattern guardAi = (AbstractAIGuardPattern) ai;

		int first = args.<Integer>getOne("first").get();
		int second = args.<Integer>getOne("second").get();
		int size = guardAi.getPositions().size();

		if (first < 0 || second < 0 || first >= size || second >= size || first == second) {
			throw new CommandException(Text.of(TextColors.RED, "Invalid indexes!"));
		}

		List<Position> positions = guardAi.getPositions();
		Position temp = positions.get(first);
		positions.set(first, positions.get(second));
		positions.set(second, temp);
	}

	public CommandSpec build() {
		return CommandSpec.builder()
				.description(Text.of(TextColors.GOLD, "NPCs | Swap Positions"))
				.permission("npc.edit.ai.position.swap")
				.arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second")))
				.executor(this)
				.build();
	}
}
