package me.mrdaniel.npcs.mixin.interfaces;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;

import javax.annotation.Nullable;

public interface NPCAble {

	@Nullable
    INPCData getData();
	void setData(INPCData data);
	void refreshEquipment();
	void refreshAI();

	<T> INPCData setProperty(PropertyType<T> property, T value);
//	INPCData setPosition(Position value);
}
