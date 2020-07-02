package me.mrdaniel.npcs.managers;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
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
	private Text select_message;
	private boolean open_menu;

	@Inject
	public SelectedManager() {
		this.selected = Maps.newHashMap();
	}

	public void load(MainConfig config) {
		this.select_message = TextUtils.toText(config.npcSelectMessage);
		this.open_menu = config.openMenuOnSelect;
	}

	public void select(Player p, INPCData data) {
		this.selected.put(p.getUniqueId(), data);

		if (this.open_menu) {
			new PropertiesChatMenu(data).send(p);
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
