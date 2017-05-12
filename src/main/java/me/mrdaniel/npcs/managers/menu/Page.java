package me.mrdaniel.npcs.managers.menu;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

import me.mrdaniel.npcs.io.NPCFile;

public abstract class Page {

	protected final Text[] lines;

	public Page(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.lines = new Text[18];

		this.reset();
		this.update(npc, file);
	}

	private void reset() {
		for (int i = 0; i < 18; i++) { this.lines[i] = Text.EMPTY; }
	}

	@Nonnull public Text[] getLines() { return this.lines; }

	public void update(@Nonnull final Living npc, @Nonnull final NPCFile file) { this.reset(); this.updatePage(npc, file); }
	protected abstract void updatePage(@Nonnull final Living npc, @Nonnull final NPCFile file);
}


//	private int count;
//	private final List<Text[]> pages;
//
//	public Page(@Nonnull final Living npc, @Nonnull final NPCFile file) {
//		current = 0;
//		this.count = 0;
//		this.pages = Lists.newArrayList();
//		this.update(npc, file);
//	}
//
//	@Nonnull
//	public List<Text[]> getPages() { return this.pages; }
//
//	protected void add(@Nonnull final Text txt) {
//		this.getPage()[this.count++%18] = txt;
//	}
//
//	private Text[] getPage() {
//		int page = this.count / 18;
//		while (this.pages.size() <= page) { this.addPage(); }
//		return this.pages.get(page);
//	}
//
//	private void addPage() {
//		Text[] page = new Text[18];
//		for (int i = 0; i < 18; i++) { page[i] = Text.EMPTY; }
//		this.pages.add(page);
//	}
//
//	public void update(@Nonnull final Living npc, @Nonnull final NPCFile file) { this.pages.clear(); this.count = 0; this.updatePage(npc, file); }
//	public abstract void updatePage(@Nonnull final Living npc, @Nonnull final NPCFile file);
