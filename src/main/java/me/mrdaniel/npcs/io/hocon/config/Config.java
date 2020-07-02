package me.mrdaniel.npcs.io.hocon.config;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config<T> {

    protected final Class<T> clazz;
    protected final HoconConfigurationLoader loader;

    protected CommentedConfigurationNode node;
    protected T value;

    public Config(Class<T> clazz, Path configDir, String fileName) {
        this.clazz = clazz;

        Path configFile = configDir.resolve(fileName);

        if (!Files.exists(configFile)) {
            this.createFile(configDir, fileName);
        }

        this.loader = HoconConfigurationLoader.builder().setPath(configDir.resolve(fileName)).build();
        this.load();
    }

    public T get() {
        return this.value;
    }

    public void save() {
        try {
            this.node.setValue(TypeToken.of(this.clazz), value);
            this.loader.save(this.node);
        } catch (IOException | ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to save config file", exc);
        }
    }

    public void load() {
        try {
            this.node = this.loader.load();
            this.value = this.node.getValue(TypeToken.of(this.clazz), this.clazz.newInstance());
            this.loader.save(this.node);
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to load config file", exc);
        } catch (IllegalAccessException | InstantiationException | ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to create default config file", exc);
        }
    }

    protected void createFile(Path configDir, String fileName) {
        try {
            Files.createDirectories(configDir);
            Files.createFile(configDir.resolve(fileName));
        } catch (IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to create config file", exc);
        }
    }
}
