package me.mrdaniel.npcs.managers.actions;

public class ActionResult {

	private final int currentAction;
	private int nextAction;
	private boolean performNextAction;
	private int waitTicks;

	public ActionResult(final int current) {
		this.currentAction = current;
		this.nextAction = current;
		this.performNextAction = true;
		this.waitTicks = 0;
	}

	public boolean getPerformNextAction() {
		return this.performNextAction;
	}

	public ActionResult setNextAction(final int nextAction) {
		this.nextAction = nextAction; return this;
	}

	public ActionResult setPerformNextAction(final boolean performNextAction) {
		this.performNextAction = performNextAction; return this;
	}

	public ActionResult setWaitTicks(final int waitTicks) {
		this.waitTicks = waitTicks; return this;
	}

	public int getCurrentAction() {
		return currentAction;
	}

	public int getNextAction() {
		return nextAction;
	}

	public int getWaitTicks() {
		return waitTicks;
	}
}
