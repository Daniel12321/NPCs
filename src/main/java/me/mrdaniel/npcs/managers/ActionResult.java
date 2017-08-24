package me.mrdaniel.npcs.managers;

import lombok.Getter;

public class ActionResult {

	@Getter private final int currentAction;
	@Getter private int nextAction;
	private boolean performNextAction;
	@Getter private int waitTicks;

	public ActionResult(final int current) {
		this.currentAction = current;
		this.nextAction = current;
		this.performNextAction = true;
		this.waitTicks = 0;
	}

	public boolean getPerformNextAction() { return this.performNextAction; }

	public ActionResult setNextAction(final int nextAction) { this.nextAction = nextAction; return this; }
	public ActionResult setPerformNextAction(final boolean performNextAction) { this.performNextAction = performNextAction; return this; }
	public ActionResult setWaitTicks(final int waitTicks) { this.waitTicks = waitTicks; return this; }
}