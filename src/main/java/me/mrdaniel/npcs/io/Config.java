package me.mrdaniel.npcs.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import me.mrdaniel.npcs.NPCs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public Config(@Nonnull final Path directory, @Nonnull final String name) {
		Path path = directory.resolve(name);
		if (!Files.exists(path)) {
			try { NPCs.getInstance().getContainer().getAsset(name).get().copyToFile(path); }
			catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to save config asset: {}", exc); }
		}

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();
		this.node = this.load();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { NPCs.getInstance().getLogger().error("Failed to load config file: {}", exc); return this.loader.createEmptyNode(); }
	}

	public CommentedConfigurationNode getNode() {
		return this.node;
	}

	public CommentedConfigurationNode getNode(@Nonnull final Object... keys) {
		return this.node.getNode(keys);
	}
}