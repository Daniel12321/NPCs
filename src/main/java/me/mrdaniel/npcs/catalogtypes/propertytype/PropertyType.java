package me.mrdaniel.npcs.catalogtypes.propertytype;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.util.annotation.CatalogedBy;

@CatalogedBy(PropertyTypes.class)
public abstract class PropertyType<T> implements CatalogType {

	private final String name;
	private final String id;
	private final Object[] hoconPath;
	private final CommandElement[] args;

	protected PropertyType(String name, String id, CommandElement... args) {
		this(name, id, new Object[]{id}, args);
	}

	protected PropertyType(String name, String id, Object[] hoconPath, CommandElement... args) {
		this.name = name;
		this.id = id;
		this.hoconPath = hoconPath;
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

	public Object[] getHoconPath() {
		return hoconPath;
	}

	public CommandElement[] getArgs() {
		return this.args;
	}

	public abstract TypeToken<T> getTypeToken();
	public abstract boolean isSupported(NPCAble npc);

	public abstract void apply(NPCAble npc, T value);
}
