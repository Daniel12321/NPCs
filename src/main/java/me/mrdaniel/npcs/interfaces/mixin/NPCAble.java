package me.mrdaniel.npcs.interfaces.mixin;

import me.mrdaniel.npcs.io.INPCData;

import javax.annotation.Nullable;

public interface NPCAble extends INPCData {

	@Nullable
    INPCData getNPCData();
	void setNPCData(INPCData data);
	void refreshNPC();

	void setNPCLooking(boolean value);
	boolean isNPCLooking();
}
