package me.mrdaniel.npcs.catalogtypes.propertytype;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(PropertyTypes.class)
public abstract class PropertyType<T> implements CatalogType {

	private final String name;
	private final String id;
	private final CommandElement[] args;

	protected PropertyType(String name, String id, CommandElement... args) {
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
	public abstract void apply(NPCAble npc, T value);
}