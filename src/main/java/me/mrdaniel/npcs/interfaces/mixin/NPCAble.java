package me.mrdaniel.npcs.interfaces.mixin;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;

import javax.annotation.Nullable;

public interface NPCAble {

	@Nullable
    INPCData getData();
	void setData(INPCData data);

	void refreshEquipment();

	<T> INPCData setProperty(PropertyType<T> property, T value);
	INPCData setPosition(Position value);
	void setLooking(boolean value);
}
