package me.mrdaniel.npcs.io.hocon.config;

import me.mrdaniel.npcs.NPCs;

import java.io.IOException;
import java.nio.file.Path;

public class DefaultConfig<T> extends Config<T> {

    public DefaultConfig(Class<T> clazz, Path configDir, String fileName) {
        super(clazz, configDir, fileName);
    }

    @Override
    protected void createFile(Path configDir, String fileName) {
        try {
            NPCs.getInstance().getContainer().getAsset(fileName).get().copyToDirectory(configDir);
        } catch (IOException exc) {
            NPCs.getInstance().getLogger().error("Failed to copy default config from assets folder", exc);
        }
    }
}
