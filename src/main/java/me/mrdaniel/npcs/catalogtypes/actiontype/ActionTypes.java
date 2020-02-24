package me.mrdaniel.npcs.catalogtypes.actiontype;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.actions.actions.*;

import java.util.List;

public final class ActionTypes {

	public static final ActionType MESSAGE = new ActionType("Message", "message", ActionMessage::new);
	public static final ActionType CONSOLECMD = new ActionType("Console Command", "consolecmd", ActionConsoleCommand::new);
	public static final ActionType PLAYERCMD = new ActionType("Player Command", "playercmd", ActionPlayerCommand::new);
	public static final ActionType CONDITION = new ActionType("Condition", "condition", ActionCondition::new);
	public static final ActionType CHOICES = new ActionType("Choices", "choices", ActionChoices::new);
	public static final ActionType PAUSE = new ActionType("Pause", "pause", ActionPause::new);
	public static final ActionType GOTO = new ActionType("Goto", "goto", ActionGoto::new);
	public static final ActionType DELAY = new ActionType("Delay", "delay", ActionDelay::new);
	public static final ActionType COOLDOWN = new ActionType("Cooldown", "cooldown", ActionCooldown::new);

	public static final List<ActionType> ALL = Lists.newArrayList(MESSAGE, CONSOLECMD, PLAYERCMD, CONDITION, CHOICES, PAUSE, GOTO, DELAY, COOLDOWN);
}
