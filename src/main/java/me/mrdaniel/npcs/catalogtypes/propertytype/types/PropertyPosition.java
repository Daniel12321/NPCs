package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.Entity;

public class PropertyPosition extends PropertyType<Position> {

	public PropertyPosition() {
		super("Position", "position");
	}

	@Override
	public TypeToken<Position> getTypeToken() {
		return TypeToken.of(Position.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return true;
	}

	@Override
	public void apply(NPCAble npc, Position value) {
		((Entity) npc).setLocationAndAngles(value.getX(), value.getY(), value.getZ(), value.getYaw(), value.getPitch());
	}
}
