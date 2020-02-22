package me.mrdaniel.npcs.managers.menu;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.text.Text;

public abstract class Page {

	protected final Text[] lines;

	public Page(NPCAble npc) {
		this.lines = new Text[18];

		this.update(npc);
	}

	private void reset() {
		for (int i = 0; i < 18; i++) {
			this.lines[i] = Text.EMPTY;
		}
	}

	public void update(NPCAble npc) {
		this.reset();
		this.updatePage(npc);
	}

	public Text[] getLines() {
		return this.lines;
	}

	protected abstract void updatePage(NPCAble npc);
}
