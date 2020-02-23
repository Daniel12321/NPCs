package me.mrdaniel.npcs.commands.action.condition;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.actions.actions.ActionCondition;
import me.mrdaniel.npcs.actions.conditions.ConditionExp;
import me.mrdaniel.npcs.actions.conditions.ConditionItem;
import me.mrdaniel.npcs.actions.conditions.ConditionLevel;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;

public abstract class CommandActionAddCondition extends CommandActionAdd {

	@Override
	public Action create(CommandContext args) {
		return new ActionCondition(this.createCondition(args), 0, 0, true);
	}

	@Nonnull
	public abstract Condition createCondition(CommandContext args);

	public static class Item extends CommandActionAddCondition {
		@Override public Condition createCondition(CommandContext args) { return new ConditionItem(args.<ItemType>getOne("type").get(), args.<Integer>getOne("amount").get(), args.<String>getOne("name").orElse(null)); }

		public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Add Item Condition Action"))
					.permission("npc.action.condition.item")
					.arguments(GenericArguments.catalogedElement(Text.of("type"), ItemType.class), GenericArguments.integer(Text.of("amount")), GenericArguments.optionalWeak(GenericArguments.string(Text.of("name"))))
					.executor(this)
					.build();
		}
	}

	public static class Money extends CommandActionAddCondition {
		@Override public Condition createCondition(CommandContext args) { return new ConditionMoney(args.<Currency>getOne("currency").get(), args.<Double>getOne("amount").get()); }

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
		@Override public Condition createCondition(CommandContext args) { return new ConditionLevel(args.<Integer>getOne("level").get()); }

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
		@Override public Condition createCondition(CommandContext args) { return new ConditionExp(args.<Integer>getOne("exp").get()); }

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
