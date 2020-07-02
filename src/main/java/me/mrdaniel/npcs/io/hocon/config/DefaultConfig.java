package me.mrdaniel.npcs.io.hocon.config;

import me.mrdaniel.npcs.NPCs;

import java.io.IOException;
import java.nio.file.Path;

public class DefaultConfig<T> extends Config<T> {

    private final Path configDir;
    private final String name;

    public DefaultConfig(Class<T> clazz, Path configDir, String name) {
        super(clazz, configDir.resolve(name));

        this.configDir = configDir;
        this.name = name;
    }

    @Override
    protected void createFile(Path file) {
        try {
            NPCs.getInstance().getContainer().getAsset(this.name).get().copyToDirectory(this.configDir);
        } catch (IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to copy default config from assets folder", exc);
        }
    }
}
