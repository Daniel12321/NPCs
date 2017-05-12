package me.mrdaniel.npcs.managers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public class MenuManager extends NPCObject {

	private final Map<UUID, NPCMenu> menus;
	private final boolean open_menu;

	public MenuManager(@Nonnull final NPCs npcs, @Nonnull final Config config) {
		super(npcs);

		this.menus = Maps.newHashMap();
		this.open_menu = config.getNode("open_menu_on_select").getBoolean(true);
	}

	public void select(@Nonnull final Player p, @Nonnull final Living npc) throws NPCException {
		NPCData data = npc.get(NPCData.class).orElseThrow(() -> new NPCException("This Entity is not an NPC!"));
		NPCFile file = super.getNPCs().getNPCManager().getFile(data.getId()).orElseThrow(() -> new NPCException("No NPC file with this id was found!"));
		this.select(p, npc, file);
	}

	public void select(@Nonnull final Player p, @Nonnull final Living npc, @Nonnull final NPCFile file) {
		NPCMenu menu = new NPCMenu(npc, file);
		this.menus.put(p.getUniqueId(), menu);

		if (this.open_menu) { menu.send(p, PageTypes.MAIN); }
		else { p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", TextColors.YELLOW, "You selected an NPC.")); }
	}

	public void deselect(@Nonnull final UUID uuid) {
		this.menus.remove(uuid);
	}

	public void deselect(@Nonnull final NPCFile file) {
		for (final UUID uuid : this.menus.keySet()) { if (this.menus.get(uuid).getFile() == file) { this.deselect(uuid); return; } }
	}

	@Nonnull
	public Optional<NPCMenu> get(@Nonnull final UUID uuid) {
		return Optional.ofNullable(this.menus.get(uuid));
	}
}