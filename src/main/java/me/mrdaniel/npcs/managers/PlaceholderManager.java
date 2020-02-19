package me.mrdaniel.npcs.managers;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.managers.placeholders.PlaceholderAPIManager;
import me.mrdaniel.npcs.managers.placeholders.PlaceholderHandler;
import me.mrdaniel.npcs.managers.placeholders.SimplePlaceholderManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class PlaceholderManager implements PlaceholderHandler {

	private static PlaceholderManager instance;

	private final PlaceholderHandler handler;

	public PlaceholderManager() {
		Optional<PluginContainer> placeholderapi = NPCs.getInstance().getGame().getPluginManager().getPlugin("placeholderapi");

		if (placeholderapi.isPresent()) {
			int version = Integer.valueOf(placeholderapi.get().getVersion().get().replaceAll("[^\\d]", ""));
			if (version < 41) {
				this.handler = new SimplePlaceholderManager();
				NPCs.getInstance().getLogger().info("Could not load PlaceholderAPI and loaded a simple version instead: Please update PlaceholderAPI to version 4.1 or higher.");
			}
			else {
				this.handler = new PlaceholderAPIManager();
				NPCs.getInstance().getLogger().info("Found and loaded PlaceholderAPI successfully.");
			}
		}
		else {
			this.handler = new SimplePlaceholderManager();
			NPCs.getInstance().getLogger().info("Could not find PlaceholderAPI and loaded a simple version instead.");
		}
	}

	public static PlaceholderManager getInstance() {
		if (instance == null) {
			instance = new PlaceholderManager();
		}
		return instance;
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
