package me.mrdaniel.npcs.io;

import com.google.inject.Inject;
import me.mrdaniel.npcs.NPCs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	@Inject
	public Config(@ConfigDir(sharedRoot = false) Path configDir, PluginContainer container, Logger logger) {
		try {
			if (!Files.exists(configDir)) {
				Files.createDirectories(configDir);
			}
			if (!Files.exists(configDir.resolve("config.conf"))) {
				container.getAsset("config.conf").get().copyToDirectory(configDir);
			}
		} catch (final IOException exc) {
			logger.error("Failed to create config asset: ", exc);
		}

		this.loader = HoconConfigurationLoader.builder().setPath(configDir.resolve("config.conf")).build();
		this.node = this.load();
	}

	public Config(Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (final IOException exc) {
				NPCs.getInstance().getLogger().error("Failed to create config file: " + path.toString(), exc);
			}
		}

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();
		this.node = this.load();
	}

	private CommentedConfigurationNode load() {
		try {
			return this.loader.load();
		} catch (final IOException exc) {
			NPCs.getInstance().getLogger().error("Failed to load config file", exc);
			return this.loader.createEmptyNode();
		}
	}

	public void save() {
		try {
			this.loader.save(this.node);
		} catch (final IOException exc) {
			NPCs.getInstance().getLogger().error("Failed to save config file", exc);
		}
	}

	public CommentedConfigurationNode getNode() {
		return this.node;
	}

	public CommentedConfigurationNode getNode(Object... keys) {
		return this.node.getNode(keys);
	}
}
