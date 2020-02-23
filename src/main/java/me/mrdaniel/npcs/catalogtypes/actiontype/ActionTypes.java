package me.mrdaniel.npcs.catalogtypes.actiontype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.actions.actions.*;

import java.util.List;

public final class ActionTypes {

	public static final ActionType MESSAGE = new ActionType("Message", "message", node -> new ActionMessage(node));
	public static final ActionType CONSOLECMD = new ActionType("Console Command", "consolecmd", node -> new ActionConsoleCommand(node));
	public static final ActionType PLAYERCMD = new ActionType("Player Command", "playercmd", node -> new ActionPlayerCommand(node));
	public static final ActionType CONDITION = new ActionType("Condition", "condition", node -> new ActionCondition(node));
	public static final ActionType CHOICES = new ActionType("Choices", "choices", node -> new ActionChoices(node));
	public static final ActionType PAUSE = new ActionType("Pause", "pause", node -> new ActionPause(node));
	public static final ActionType GOTO = new ActionType("Goto", "goto", node -> new ActionGoto(node));
	public static final ActionType DELAY = new ActionType("Delay", "delay", node -> new ActionDelay(node));
	public static final ActionType COOLDOWN = new ActionType("Cooldown", "cooldown", node -> new ActionCooldown(node));

	public static final List<ActionType> ALL = Lists.newArrayList(MESSAGE, CONSOLECMD, PLAYERCMD, CONDITION, CHOICES, PAUSE, GOTO, DELAY, COOLDOWN);
}
