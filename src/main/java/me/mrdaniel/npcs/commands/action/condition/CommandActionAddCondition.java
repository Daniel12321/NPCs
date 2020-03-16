package me.mrdaniel.npcs.commands.action.condition;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.actions.actions.ActionCondition;
import me.mrdaniel.npcs.actions.conditions.ConditionExp;
import me.mrdaniel.npcs.actions.conditions.ConditionItem;
import me.mrdaniel.npcs.actions.conditions.ConditionLevel;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class CommandActionAddCondition extends CommandActionAdd {

	@Override
	public Action create(Player player, CommandContext args) throws CommandException {
		return new ActionCondition(this.createCondition(player, args), 0, 0, true);
	}

	public abstract Condition createCondition(Player player, CommandContext args) throws CommandException;

	public static class Item extends CommandActionAddCondition {

		@Override
		public Condition createCondition(Player player, CommandContext args) throws CommandException {
			return new ConditionItem(player.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item to do that."))), args.<Integer>getOne("amount").orElse(1));
		}

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Item Condition Action"))
					.permission("npc.action.condition.item")
					.arguments(GenericArguments.integer(Text.of("amount")))
					.executor(this)
					.build();
		}
	}

	public static class Money extends CommandActionAddCondition {

		@Override
		public Condition createCondition(Player player, CommandContext args) {
			return new ConditionMoney(args.<Currency>getOne("currency").get(), args.<Double>getOne("amount").get());
		}

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Money Condition Action"))
					.permission("npc.action.condition.money")
					.arguments(GenericArguments.catalogedElement(Text.of("type"), Currency.class), GenericArguments.doubleNum(Text.of("amount")))
					.executor(this)
					.build();
		}
	}

	public static class Level extends CommandActionAddCondition {

		@Override
		public Condition createCondition(Player player, CommandContext args) {
			return new ConditionLevel(args.<Integer>getOne("level").get());
		}

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Level Condition Action"))
					.permission("npc.action.condition.level")
					.arguments(GenericArguments.integer(Text.of("level")))
					.executor(this)
					.build();
		}
	}

	public static class Exp extends CommandActionAddCondition {

		@Override
		public Condition createCondition(Player player, CommandContext args) {
			return new ConditionExp(args.<Integer>getOne("exp").get());
		}

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Exp Condition Action"))
					.permission("npc.action.condition.exp")
					.arguments(GenericArguments.integer(Text.of("exp")))
					.executor(this)
					.build();
		}
	}
}
