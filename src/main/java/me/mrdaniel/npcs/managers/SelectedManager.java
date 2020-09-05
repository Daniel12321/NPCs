package me.mrdaniel.npcs.managers;

import com.google.common.collect.Maps;
import me.mrdaniel.npcs.gui.chat.npc.NPCChatMenu;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SelectedManager {

	private final Map<UUID, NPCChatMenu> menus;
	private Text select_message;
	private boolean open_menu;

	public SelectedManager(MainConfig config) {
		this.menus = Maps.newHashMap();

		this.select_message = TextUtils.toText(config.npc_select_message);
		this.open_menu = config.open_menu_on_select;
	}

	public void select(Player player, INPCData data) {
		NPCChatMenu menu = this.getOrCreate(player, data);

		if (this.open_menu) {
			menu.open();
		} else {
			player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.DARK_GRAY, "[", TextColors.GOLD, "NPCs", TextColors.DARK_GRAY, "] ", this.select_message));
		}
	}

	public boolean deselect(UUID uuid) {
		return this.menus.remove(uuid) != null;
	}

	public void deselect(INPCData data) {
		this.menus.entrySet().stream().filter(e -> e.getValue().getData() == data).map(Map.Entry::getKey).collect(Collectors.toList()).forEach(this::deselect);
	}

	public Optional<NPCChatMenu> get(UUID uuid) {
		return Optional.ofNullable(this.menus.get(uuid));
	}

	public NPCChatMenu getOrCreate(Player player, INPCData data) {
		return this.menus.computeIfAbsent(player.getUniqueId(), k -> new NPCChatMenu(player, data));
	}
}
