package me.mrdaniel.npcs.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import me.mrdaniel.npcs.NPCs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public Config(@Nonnull final NPCs npcs, @Nonnull final Path path) {
		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { npcs.getContainer().getAsset("config.conf").get().copyToFile(path); }
			catch (final IOException exc) { npcs.getLogger().error("Failed to save config asset: {}", exc); }
		}
		this.node = this.load(npcs.getLogger());
	}

	private CommentedConfigurationNode load(@Nonnull final Logger logger) {
		try { return this.loader.load(); }
		catch (final IOException exc) { logger.error("Failed to load config file: {}", exc); return this.loader.createEmptyNode(); }
	}

	@Nonnull
	public CommentedConfigurationNode getNode(@Nonnull final Object... keys) {
		return this.node.getNode(keys);
	}
}