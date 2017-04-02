package me.mrdaniel.npcs.data.action;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;

public interface NPCAction extends DataSerializable {

	void execute(NPCs npcs, Player p);

	default int getContentVersion() {
		return 1;
	}

	default DataContainer toContainer() {
		return addData(new MemoryDataContainer().set(Queries.CONTENT_VERSION, getContentVersion()));
	}

	DataContainer addData(DataContainer container);
}