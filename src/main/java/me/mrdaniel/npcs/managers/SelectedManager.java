package me.mrdaniel.npcs.managers;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SelectedManager {

	private final Map<UUID, INPCData> selected;
	private final Text select_message;
	private final boolean open_menu;

	public SelectedManager(Config config) {
		this.selected = Maps.newHashMap();
		this.select_message = TextUtils.toText(config.getNode("npc_select_message").getString("&eYou selected an NPC."));
		this.open_menu = config.getNode("open_menu_on_select").getBoolean(true);
	}

	public void select(Player p, INPCData data) throws NPCException {
		this.select(p, NPCs.getInstance().getNPCManager().getNPC(data).orElseThrow(() -> new NPCException("Failed to select NPC: NPC hasn't been spawned.")));
	}

	public void select(Player p, NPCAble npc) {
		this.selected.put(p.getUniqueId(), npc.getNPCData());

		if (this.open_menu) {
			new PropertiesChatMenu(npc).send(p);
		} else {
			p.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", this.select_message));
		}
	}

	public boolean deselect(UUID uuid) {
		return this.selected.remove(uuid) != null;
	}

	public boolean deselect(INPCData data) {
		for (UUID uuid : this.selected.keySet()) {
			if (this.selected.get(uuid) == data) {
				return this.deselect(uuid);
			}
		}
		return false;
	}

	public Optional<INPCData> get(UUID uuid) {
		return Optional.ofNullable(this.selected.get(uuid));
	}
}
