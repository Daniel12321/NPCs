package me.mrdaniel.npcs.managers.placeholders;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.managers.IPlaceholderManager;
import me.mrdaniel.npcs.utils.TextUtils;
import me.rojo8399.placeholderapi.PlaceholderService;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class PlaceholderAPIHandler implements IPlaceholderManager {

	private final PlaceholderService service;
	private final String msg_format;
	private final String choice_format;

	public PlaceholderAPIHandler(MainConfig config) {
		this.service = NPCs.getInstance().getGame().getServiceManager().provide(PlaceholderService.class).get();
		this.msg_format = config.messages.npcMessageFormat;
		this.choice_format = config.messages.npcChoiceFormat;
	}

	@Override
	public String formatCommand(Player p, String txt) {
		return TextUtils.toString(this.format(p, txt));
	}

	@Override
	public Text formatNPCMessage(Player p, String message, String npc_name) {
		return this.format(p, this.msg_format.concat(message).replace("%npc_name%", npc_name));
	}

	@Override
	public Text formatChoiceMessage(Player p, Text choices) {
		return Text.builder().append(this.format(p, this.choice_format), choices).build();
	}

	private Text format(Player p, String txt) {
		return this.service.replacePlaceholders(txt, p, p);
	}
}
