package me.mrdaniel.npcs.managers.menu;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.catalogtypes.menupages.PageType;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.io.NPCFile;

public class NPCMenu {

	private final Living npc;
	private final NPCFile file;
	private final Map<PageType, Page> pages;

	public NPCMenu(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		this.npc = npc;
		this.file = file;
		this.pages = Maps.newHashMap();
		this.pages.put(PageTypes.MAIN, new MainPage(npc, file));
		if (npc instanceof ArmorEquipable) { this.pages.put(PageTypes.ARMOR, new ArmorPage(npc, file)); }
		this.pages.put(PageTypes.ACTIONS, new ActionsPage(npc, file));
	}

	public void send(@Nonnull final Player p, @Nonnull final PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> this.send(p, pg));
	}

	public void send(@Nonnull final Player p, @Nonnull final Page page) {
		p.sendMessage(Text.EMPTY);
		p.sendMessage(Text.of(Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Menu", TextColors.YELLOW, " ]=====---------------")));
		p.sendMessages(page.lines);
		p.sendMessage(this.getBottomLine());
	}

	@Nonnull
	private Text getBottomLine() {
		Text.Builder b = Text.builder().append(Text.of(TextColors.YELLOW, "----")).append(this.getPageText(PageTypes.MAIN));
		Optional.ofNullable(this.pages.get(PageTypes.ARMOR)).ifPresent(pg -> b.append(this.getPageText(PageTypes.ARMOR)));
		return b.append(this.getPageText(PageTypes.ACTIONS)).append(Text.of(TextColors.YELLOW, "----")).build();
	}

	@Nonnull
	private Text getPageText(@Nonnull final PageType page) {
		return Text.builder()
				.append(Text.of(TextColors.YELLOW, "--=[ "))
				.append(Text.builder()
						.append(Text.of(TextColors.RED, page.getName()))
						.onHover(TextActions.showText(Text.of(TextColors.GOLD, "Open")))
						.onClick(TextActions.executeCallback(src -> this.send((Player)src, page)))
						.build())
				.append(Text.of(TextColors.YELLOW, " ]=--"))
				.build();
	}

	public void update(@Nonnull final PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> pg.update(this.npc, this.file));
	}

	public void updateAndSend(@Nonnull final Player p, @Nonnull final PageType page) {
		Optional.ofNullable(this.pages.get(page)).ifPresent(pg -> { pg.update(this.npc, this.file); this.send(p, pg); });
	}

	@Nonnull public Living getNPC() { return this.npc; }
	@Nonnull public NPCFile getFile() { return this.file; }
}