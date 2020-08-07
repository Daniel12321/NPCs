package me.mrdaniel.npcs.io.hocon.config;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config<T> {

    protected final Class<T> clazz;
    protected final Path path;
    protected final HoconConfigurationLoader loader;

    protected CommentedConfigurationNode node;
    protected T value;

    public Config(Class<T> clazz, Path configDir, String fileName) {
        this(null, clazz, configDir, fileName);
    }

    public Config(T value, Path configDir, String fileName) {
        this(value, (Class<T>) value.getClass(), configDir, fileName);
    }

    private Config(@Nullable T value, Class<T> clazz, Path configDir, String fileName) {
        this.clazz = clazz;
        this.path = configDir.resolve(fileName);
        this.loader = HoconConfigurationLoader.builder().setPath(configDir.resolve(fileName)).build();
        this.node = this.loader.createEmptyNode();
        this.value = value;

        if (!Files.exists(this.path)) {
            this.createFile(configDir, fileName);
        }
        if (this.value != null) {
            this.save();
        } else {
            this.load();
        }
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
            this.value = this.node.getValue(TypeToken.of(this.clazz), this.clazz.getConstructor().newInstance());
            this.loader.save(this.node);
        } catch (final IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to load config file", exc);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | ObjectMappingException exc) {
            NPCs.getInstance().getLogger().error("Failed to create default config file", exc);
        }
    }

    public void delete() {
        try {
            Files.deleteIfExists(this.path);
        } catch (IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to delete config file", exc);
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
