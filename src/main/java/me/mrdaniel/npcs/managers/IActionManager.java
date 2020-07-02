package me.mrdaniel.npcs.managers;

import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;

import java.util.UUID;

public interface IActionManager {

	void execute(UUID uuid, INPCData data) throws NPCException;
	void executeChoice(INPCData data, UUID uuid, int next) throws NPCException;

	void setChoosing(UUID uuid, INPCData file);
	void removeChoosing(UUID uuid);
	void removeChoosing(INPCData data);
}
