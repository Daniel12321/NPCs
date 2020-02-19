package me.mrdaniel.npcs.catalogtypes.optiontype;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.util.annotation.CatalogedBy;

import java.util.Optional;

@CatalogedBy(OptionTypes.class)
public abstract class OptionType<T> implements CatalogType {

	private final String name;
	private final String id;
	private final CommandElement[] args;

	protected OptionType(String name, String id, CommandElement... args) {
		this.name = name;
		this.id = id;
		this.args = args;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public CommandElement[] getArgs() {
		return this.args;
	}

	public abstract boolean isSupported(NPCAble npc);

	public abstract Optional<T> readFromFile(NPCFile file);
	public abstract void writeToNPC(NPCAble npc, T value);
	public abstract void writeToFile(NPCFile file, T value);

	public void writeToNPCFromFile(NPCAble npc, NPCFile file) {
		if (this.isSupported(npc)) {
			this.readFromFile(file).ifPresent(value -> this.writeToNPC(npc, value));
		}
	}

	public void writeToFileAndNPC(NPCAble to, NPCFile from) {
		this.readFromFile(from).ifPresent(value -> this.writeToFileAndNPC(to, value));
	}

	public void writeToFileAndNPC(NPCAble npc, final T value) {
		if (this.isSupported(npc)) {
			this.writeToNPC(npc, value); this.writeToFile(npc.getNPCFile(), value);
		}
	}
}
