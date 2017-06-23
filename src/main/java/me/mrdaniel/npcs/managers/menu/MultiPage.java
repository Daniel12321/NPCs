package me.mrdaniel.npcs.managers.menu;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Lists;

import me.mrdaniel.npcs.io.NPCFile;

public abstract class MultiPage extends Page {

	protected List<Text[]> pages;
	protected int count;
	protected int current;

	public MultiPage(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		super(npc, file);
	}

	@Nonnull
	@Override
	public Text[] getLines() {
		return this.pages.get(this.current);
	}

	@Override
	public void update(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.pages = Lists.newArrayList();
		this.count = 0;
		this.current = 0;
		this.updatePage(npc, file);
	}

	protected void add(@Nonnull final Text txt) {
		int page = this.count / 18;
		while (this.pages.size() <= page) { this.addPage(); }
		this.pages.get(page)[this.count++ % 18] = txt;
	}

	protected void addPage() {
		Text[] page = new Text[18];
		for (int i = 0; i < 18; i++) { page[i] = Text.EMPTY; }
		this.pages.add(page);
	}

	public void addCurrent(final int add) {
		this.current += add;
		if (this.current < 0) { this.current = 0; }
		else if (this.current >= this.pages.size()) { this.current = this.pages.size()-1; }
	}
}