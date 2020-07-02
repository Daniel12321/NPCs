package me.mrdaniel.npcs.catalogtypes.propertytype;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.io.database.DatabaseNPCData;
import me.mrdaniel.npcs.io.hocon.HoconNPCData;
import me.mrdaniel.npcs.io.nbt.NBTNPCData;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.util.annotation.CatalogedBy;

import javax.annotation.Nullable;

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

	public abstract TypeToken<T> getTypeToken();
	public abstract boolean isSupported(NPCAble npc);
	public abstract boolean isSupported(NPCType type);

	public abstract void apply(NPCAble npc, T value);

	public abstract void setHocon(HoconNPCData data, T value);
	public abstract void setDatabase(DatabaseNPCData data, T value);
	public abstract void setNBT(NBTNPCData data, T value);

	@Nullable public abstract T getHocon(HoconNPCData data);
	@Nullable public abstract T getDatabase(DatabaseNPCData data);
	@Nullable public abstract T getNBT(NBTNPCData data);
}
