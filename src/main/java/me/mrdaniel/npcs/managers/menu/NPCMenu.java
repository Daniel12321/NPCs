package me.mrdaniel.npcs.managers.menu;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;
import java.util.Optional;

public class NPCMenu {

	private final NPCAble npc;
	private final Map<PageType, Page> pages;

	public NPCMenu(NPCAble npc) {
		this.npc = npc;
		this.pages = Maps.newHashMap();
		this.pages.put(PageTypes.MAIN, new MainPage(npc));
		if (npc instanceof ArmorEquipable) { this.pages.put(PageTypes.ARMOR, new ArmorPage(npc)); }
		this.pages.put(PageTypes.ACTIONS, new ActionsPage(npc));
	}

	public void send(Player p, PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> this.send(p, pg));
	}

	public void send(Player p, Page page) {
		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.of(Text.of(TextColors.YELLOW, "----------------=====[ ", TextColors.RED, "NPC Menu", TextColors.YELLOW, " ]=====----------------")));
		p.sendMessages(page.getLines());
		p.sendMessage(this.getBottomLine());
	}

	private Text getBottomLine() {
		Text.Builder b = Text.builder().append(this.getButton(TextUtils.getButton("Main", src -> this.send((Player)src, PageTypes.MAIN))));
		Optional.ofNullable(this.pages.get(PageTypes.ARMOR)).ifPresent(pg -> b.append(this.getButton(TextUtils.getButton("Armor", src -> this.send((Player)src, PageTypes.ARMOR)))));
		b.append(this.getActionsButton(TextUtils.getButton("Actions", src -> this.send((Player)src, PageTypes.ACTIONS))));

		String bar = this.getBar((56 - b.build().toPlain().length()) / 2);

		return Text.builder().append(Text.of(TextColors.YELLOW, bar), b.build(), Text.of(TextColors.YELLOW, bar)).build();
	}

	private String getBar(int times) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < times; i++) { b.append('-'); }
		return b.toString();
	}

	private Text getButton(Text button) {
		return Text.builder().append(Text.of(TextColors.YELLOW, "-=[ "), button, Text.of(TextColors.YELLOW, " ]=-")).build();
	}

	private Text getActionsButton(Text button) {
		return this.getButton(Text.builder().append(
				Text.builder().append(Text.of(TextColors.AQUA, "<- "))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Previous Page")))
				.onClick(TextActions.executeCallback(src -> { ((ActionsPage)this.pages.get(PageTypes.ACTIONS)).addCurrent(-1); this.send((Player)src, PageTypes.ACTIONS); })).build(),
				TextUtils.getButton("Actions", src -> this.send((Player)src, PageTypes.ACTIONS)),
				Text.builder().append(Text.of(TextColors.AQUA, " ->"))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Next Page")))
				.onClick(TextActions.executeCallback(src -> { ((ActionsPage)this.pages.get(PageTypes.ACTIONS)).addCurrent(1); this.send((Player)src, PageTypes.ACTIONS); })).build())
				.build());
	}

	public void update(PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> pg.update(this.npc));
	}

	public void updateAndSend(Player p, PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> { pg.update(this.npc); this.send(p, pg); });
	}

	public NPCAble getNpc() {
		return this.npc;
	}
}
