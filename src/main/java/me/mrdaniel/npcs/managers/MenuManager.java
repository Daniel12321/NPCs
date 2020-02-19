package me.mrdaniel.npcs.managers;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.menu.NPCMenu;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MenuManager {

	private static MenuManager instance;

	private final Map<UUID, NPCMenu> menus;
	private final Text select_message;
	private final boolean open_menu;

	public MenuManager() {
		Config config = NPCs.getInstance().getConfig();

		this.menus = Maps.newHashMap();
		this.select_message = TextUtils.toText(config.getNode("npc_select_message").getString("&eYou selected an NPC."));
		this.open_menu = config.getNode("open_menu_on_select").getBoolean(true);
	}

	public static MenuManager getInstance() {
		if (instance == null) {
			instance = new MenuManager();
		}
		return instance;
	}

	public void select(Player p, NPCFile file) throws NPCException {
		this.select(p, (NPCAble) NPCManager.getInstance().getNPC(file).orElseThrow(() -> new NPCException("Failed to select NPC: NPC hasn't been spawned.")));
	}

	public void select(Player p, NPCAble npc) {
		NPCMenu menu = new NPCMenu(npc);
		this.menus.put(p.getUniqueId(), menu);

		if (this.open_menu) {
			menu.send(p, PageTypes.MAIN);
		} else {
			p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", this.select_message));
		}
	}

	public boolean deselect(UUID uuid) {
		return this.menus.remove(uuid) != null;
	}

	public boolean deselect(NPCAble npc) {
		for (final UUID uuid : this.menus.keySet()) {
			if (this.menus.get(uuid).getNpc() == npc) {
				return this.deselect(uuid);
			}
		}
		return false;
	}

	public Optional<NPCMenu> get(UUID uuid) {
		return Optional.ofNullable(this.menus.get(uuid));
	}
}
