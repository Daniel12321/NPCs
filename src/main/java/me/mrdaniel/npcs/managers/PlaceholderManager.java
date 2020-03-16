package me.mrdaniel.npcs.managers;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.managers.placeholders.PlaceholderAPIManager;
import me.mrdaniel.npcs.managers.placeholders.PlaceholderHandler;
import me.mrdaniel.npcs.managers.placeholders.SimplePlaceholderManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class PlaceholderManager implements PlaceholderHandler {

	private PlaceholderHandler handler;

	public void load(MainConfig config) {
		Optional<PluginContainer> placeholderapi = NPCs.getInstance().getGame().getPluginManager().getPlugin("placeholderapi");

		if (placeholderapi.isPresent()) {
			int version = Integer.parseInt(placeholderapi.get().getVersion().get().replaceAll("[^\\d]", ""));
			if (version < 41) {
				this.handler = new SimplePlaceholderManager(config);
				NPCs.getInstance().getLogger().info("Could not load PlaceholderAPI and loaded a simple version instead: Please update PlaceholderAPI to version 4.1 or higher.");
			}
			else {
				this.handler = new PlaceholderAPIManager(config);
				NPCs.getInstance().getLogger().info("Found and loaded PlaceholderAPI successfully.");
			}
		}
		else {
			this.handler = new SimplePlaceholderManager(config);
			NPCs.getInstance().getLogger().info("Could not find PlaceholderAPI and loaded a simple version instead.");
		}
	}

	@Override
	public String formatCommand(final Player p, final String txt) {
		return this.handler.formatCommand(p, txt);
	}

	@Override
	public Text formatNPCMessage(final Player p, final String txt, final String npc_name) {
		return this.handler.formatNPCMessage(p, txt, npc_name);
	}

	@Override
	public Text formatChoiceMessage(final Player p, final Text choices) {
		return this.handler.formatChoiceMessage(p, choices);
	}

	public PlaceholderHandler getHandler() {
		return this.handler;
	}
}
