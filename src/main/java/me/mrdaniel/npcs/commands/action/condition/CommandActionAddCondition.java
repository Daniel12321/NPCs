package me.mrdaniel.npcs.commands.action.condition;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.item.ItemType;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionCondition;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionExp;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionItem;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionLevel;

public abstract class CommandActionAddCondition extends CommandActionAdd {

	public CommandActionAddCondition(@Nonnull final NPCs npcs) {
		super(npcs);
	}

	@Override
	public Action create(final CommandContext args) {
		return new ActionCondition(this.createCondition(args), 0, 0, true);
	}

	@Nonnull
	public abstract me.mrdaniel.npcs.data.npc.actions.conditions.Condition createCondition(@Nonnull final CommandContext args);

	public static class Item extends CommandActionAddCondition {
		public Item(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public me.mrdaniel.npcs.data.npc.actions.conditions.Condition createCondition(final CommandContext args) { return new ConditionItem(args.<ItemType>getOne("type").get(), args.<Integer>getOne("amount").get(), args.<String>getOne("name").orElse(null)); }
	}

	public static class Level extends CommandActionAddCondition {
		public Level(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public me.mrdaniel.npcs.data.npc.actions.conditions.Condition createCondition(final CommandContext args) { return new ConditionLevel(args.<Integer>getOne("level").get()); }
	}

	public static class Exp extends CommandActionAddCondition {
		public Exp(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public me.mrdaniel.npcs.data.npc.actions.conditions.Condition createCondition(final CommandContext args) { return new ConditionExp(args.<Integer>getOne("exp").get()); }
	}
}