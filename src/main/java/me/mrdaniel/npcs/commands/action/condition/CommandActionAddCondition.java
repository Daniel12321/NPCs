package me.mrdaniel.npcs.commands.action.condition;

import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionCondition;
import me.mrdaniel.npcs.actions.conditions.*;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.service.economy.Currency;

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
	}

	public static class Money extends CommandActionAddCondition {
		@Override public Condition createCondition(CommandContext args) { return new ConditionMoney(args.<Currency>getOne("currency").get(), args.<Double>getOne("amount").get()); }
	}

	public static class Level extends CommandActionAddCondition {
		@Override public Condition createCondition(CommandContext args) { return new ConditionLevel(args.<Integer>getOne("level").get()); }
	}

	public static class Exp extends CommandActionAddCondition {
		@Override public Condition createCondition(CommandContext args) { return new ConditionExp(args.<Integer>getOne("exp").get()); }
	}
}
