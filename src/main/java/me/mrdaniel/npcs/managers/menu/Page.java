package me.mrdaniel.npcs.managers.menu;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.io.NPCFile;

public abstract class Page {

	protected final Text[] lines;

	public Page(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.lines = new Text[18];

		this.update(npc, file);
	}

	private void reset() {
		for (int i = 0; i < 18; i++) { this.lines[i] = Text.EMPTY; }
	}

	@Nonnull
	public Text[] getLines() {
		return this.lines;
	}

	public void update(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.reset();
		this.updatePage(npc, file);
	}

	protected abstract void updatePage(@Nonnull final Living npc, @Nonnull final NPCFile file);
}