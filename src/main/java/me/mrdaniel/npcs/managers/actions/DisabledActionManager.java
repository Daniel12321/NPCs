package me.mrdaniel.npcs.managers.actions;

import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.managers.IActionManager;

import java.util.UUID;

public class DisabledActionManager implements IActionManager {

    @Override
    public void execute(UUID uuid, INPCData data) throws NPCException {

    }

    @Override
    public void executeChoice(INPCData data, UUID uuid, int next) throws NPCException {

    }

    @Override
    public void setChoosing(UUID uuid, INPCData file) {

    }

    @Override
    public void removeChoosing(UUID uuid) {

    }

    @Override
    public void removeChoosing(INPCData data) {

    }
}
