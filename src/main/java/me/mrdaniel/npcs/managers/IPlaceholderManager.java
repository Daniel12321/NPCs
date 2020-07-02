package me.mrdaniel.npcs.managers;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.managers.placeholders.PlaceholderAPIHandler;
import me.mrdaniel.npcs.managers.placeholders.SimplePlaceholderHandler;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public interface IPlaceholderManager {

	String formatCommand(Player p, String txt);
	Text formatNPCMessage(Player p, String txt, String npc_name);
	Text formatChoiceMessage(Player p, Text choices);

	static IPlaceholderManager load(MainConfig config) {
		int version = NPCs.getInstance().getGame().getPluginManager()
				.getPlugin("placeholderapi")
				.flatMap(PluginContainer::getVersion)
				.map(v -> Integer.parseInt(v.replaceAll("[^\\d]", "")))
				.orElse(0);

		if (version < 41) {
			NPCs.getInstance().getLogger().info("Could not load PlaceholderAPI and loaded a simple version instead: Please update PlaceholderAPI to version 4.1 or higher.");
			return new SimplePlaceholderHandler(config);
		}
		else {
			NPCs.getInstance().getLogger().info("Found and loaded PlaceholderAPI successfully.");
			return new PlaceholderAPIHandler(config);
		}
	}
}
