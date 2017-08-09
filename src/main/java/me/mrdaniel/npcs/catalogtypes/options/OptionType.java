package me.mrdaniel.npcs.catalogtypes.options;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import lombok.Getter;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

@CatalogedBy(OptionTypes.class)
public class OptionType<T> implements CatalogType {

	@Getter private final String name;
	@Getter private final String id;
	private final Predicate<NPCAble> supports;
	private final BiConsumer<NPCAble, T> writeToNPC;
	private final BiConsumer<NPCFile, T> writeToFile;
	private final Function<NPCFile, T> readFromFile;

	protected OptionType(@Nonnull final String name,
			             @Nonnull final String id,
			             @Nonnull final Predicate<NPCAble> supports,
			             @Nonnull final BiConsumer<NPCAble, T> writeToNPC,
			             @Nonnull final BiConsumer<NPCFile, T> writeToFile,
			             @Nonnull final Function<NPCFile, T> readFromFile) {
		this.name = name;
		this.id = id;
		this.supports = supports;
		this.writeToNPC = writeToNPC;
		this.writeToFile = writeToFile;
		this.readFromFile = readFromFile;
	}

	@Nullable
	public Optional<T> getFromFile(@Nonnull final NPCFile file) {
		return Optional.ofNullable(this.readFromFile.apply(file));
	}

	public boolean supports(@Nonnull final NPCAble npc) {
		return this.supports.test(npc);
	}

	public void setNPC(@Nonnull final NPCAble npc, @Nonnull final T value) {
		this.writeToNPC.accept(npc, value);
	}

	public void setFile(@Nonnull final NPCFile file, @Nonnull final T value) {
		this.writeToFile.accept(file, value);
	}

	public void setFromFile(@Nonnull final NPCFile from, @Nonnull final NPCFile to) {
		this.getFromFile(from).ifPresent(value -> this.setFile(to, value));
	}

	public void setFromFile(@Nonnull final NPCAble npc, @Nonnull final NPCFile file) {
		if (this.supports(npc)) { this.getFromFile(file).ifPresent(v -> this.setNPC(npc, v)); }
	}

	public void setFileAndNPC(@Nonnull final NPCAble npc, @Nonnull final T value) {
		this.setNPC(npc, value);
		this.setFile(npc.getNPCFile(), value);
	}
}