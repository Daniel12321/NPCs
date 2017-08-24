package me.mrdaniel.npcs.catalogtypes.actiontype;

import me.mrdaniel.npcs.actions.ActionChoices;
import me.mrdaniel.npcs.actions.ActionCondition;
import me.mrdaniel.npcs.actions.ActionConsoleCommand;
import me.mrdaniel.npcs.actions.ActionCooldown;
import me.mrdaniel.npcs.actions.ActionDelay;
import me.mrdaniel.npcs.actions.ActionGoto;
import me.mrdaniel.npcs.actions.ActionMessage;
import me.mrdaniel.npcs.actions.ActionPause;
import me.mrdaniel.npcs.actions.ActionPlayerCommand;

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
}