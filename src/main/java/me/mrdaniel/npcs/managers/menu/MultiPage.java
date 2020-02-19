package me.mrdaniel.npcs.managers.menu;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.text.Text;

import java.util.List;

public abstract class MultiPage extends Page {

	protected List<Text[]> pages;
	protected int count;
	protected int current;

	public MultiPage(NPCAble npc) {
		super(npc);
	}

	@Override
	public Text[] getLines() {
		return this.pages.get(this.current);
	}

	@Override
	public void update(NPCAble npc) {
		this.pages = Lists.newArrayList();
		this.count = 0;
		this.current = 0;
		this.updatePage(npc);
	}

	protected void add(Text txt) {
		int page = this.count / 18;
		while (this.pages.size() <= page) { this.addPage(); }
		this.pages.get(page)[this.count++ % 18] = txt;
	}

	protected void addPage() {
		Text[] page = new Text[18];
		for (int i = 0; i < 18; i++) { page[i] = Text.EMPTY; }
		this.pages.add(page);
	}

	public void addCurrent(int add) {
		this.current += add;
		if (this.current < 0) { this.current = 0; }
		else if (this.current >= this.pages.size()) { this.current = this.pages.size()-1; }
	}
}
