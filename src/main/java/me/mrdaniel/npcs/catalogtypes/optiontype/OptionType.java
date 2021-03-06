package me.mrdaniel.npcs.catalogtypes.optiontype;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

@CatalogedBy(OptionTypes.class)
public abstract class OptionType<T> implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;

	public OptionType(@Nonnull final String name, @Nonnull final String id) {
		this.name = name;
		this.id = id;
	}

	public abstract boolean isSupported(@Nonnull final NPCAble npc);

	public abstract Optional<T> readFromFile(@Nonnull final NPCFile file);
	public abstract void writeToNPC(@Nonnull final NPCAble npc, @Nonnull final T value);
	public abstract void writeToFile(@Nonnull final NPCFile file, @Nonnull final T value);

	public void writeToNPCFromFile(@Nonnull final NPCAble npc, @Nonnull final NPCFile file) {
		if (this.isSupported(npc)) { this.readFromFile(file).ifPresent(value -> this.writeToNPC(npc, value)); }
	}

	public void writeToFileAndNPC(@Nonnull final NPCAble to, @Nonnull final NPCFile from) {
		this.readFromFile(from).ifPresent(value -> this.writeToFileAndNPC(to, value));
	}

	public void writeToFileAndNPC(@Nonnull final NPCAble npc, @Nonnull final T value) {
		if (this.isSupported(npc)) { this.writeToNPC(npc, value); this.writeToFile(npc.getNPCFile(), value); }
	}
}