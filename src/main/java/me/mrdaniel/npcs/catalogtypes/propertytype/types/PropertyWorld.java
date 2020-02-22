package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import net.minecraft.entity.Entity;
import org.spongepowered.api.world.World;

public class PropertyWorld extends PropertyType<World> {

	public PropertyWorld() {
		super("World", "world");
	}

	@Override
	public TypeToken<World> getTypeToken() {
		return TypeToken.of(World.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, World value) {
		((Entity) npc).setWorld((net.minecraft.world.World)value);
		npc.getNPCData().setProperty(PropertyTypes.WORLD, value);
	}
}
