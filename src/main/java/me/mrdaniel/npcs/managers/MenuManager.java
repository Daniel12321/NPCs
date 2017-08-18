package me.mrdaniel.npcs.managers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Maps;

import lombok.Getter;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
import me.mrdaniel.npcs.utils.TextUtils;

public class MenuManager {

	@Getter private static MenuManager instance = new MenuManager();

	private final Map<UUID, NPCMenu> menus;
	private final Text select_message;
	private final boolean open_menu;

	public MenuManager() {
		Config config = NPCs.getInstance().getConfig();

		this.menus = Maps.newHashMap();
		this.select_message = TextUtils.toText(config.getNode("npc_select_message").getString("&eYou selected an NPC."));
		this.open_menu = config.getNode("open_menu_on_select").getBoolean(true);
	}

	public void select(@Nonnull final Player p, @Nonnull final NPCFile file) throws NPCException {
		this.select(p, (NPCAble) NPCManager.getInstance().getNPC(file, true).orElseThrow(() -> new NPCException("Failed to select NPC: NPC hasn't been spawned.")));
	}

	public void select(@Nonnull final Player p, @Nonnull final NPCAble npc) {
		NPCMenu menu = new NPCMenu(npc);
		this.menus.put(p.getUniqueId(), menu);

		if (this.open_menu) { menu.send(p, PageTypes.MAIN); }
		else { p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", this.select_message)); }
	}

	public void deselect(@Nonnull final UUID uuid) {
		this.menus.remove(uuid);
	}

	public void deselect(@Nonnull final NPCAble npc) {
		for (final UUID uuid : this.menus.keySet()) { if (this.menus.get(uuid).getNpc() == npc) { this.deselect(uuid); return; } }
	}

	public Optional<NPCMenu> get(@Nonnull final UUID uuid) {
		return Optional.ofNullable(this.menus.get(uuid));
	}
}