package me.mrdaniel.npcs.managers;

public class ActionResult {

	private final int current;
	private int next;
	private boolean perform_next;
	private int wait_ticks;

	public ActionResult(int current) {
		this.current = current;
		this.next = current;
		this.perform_next = true;
		this.wait_ticks = 0;
	}

	public int getCurrent() { return this.current; }
	protected int getNext() { return this.next; }
	protected boolean getPerformNext() { return this.perform_next; }
	protected int getWaitTicks() { return this.wait_ticks; }

	public ActionResult setNext(final int next) { this.next = next; return this; }
	public ActionResult setPerformNext(final boolean perform_next) { this.perform_next = perform_next; return this; }
	public ActionResult setWaitTicks(final int wait_ticks) { this.wait_ticks = wait_ticks; return this; }
}