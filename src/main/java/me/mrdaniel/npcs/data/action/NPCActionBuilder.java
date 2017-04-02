package me.mrdaniel.npcs.data.action;

import java.util.Optional;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import me.mrdaniel.npcs.data.MMOKeys;

public class NPCActionBuilder implements DataBuilder<NPCAction> {

	@Override
	public Optional<NPCAction> build(final DataView view) throws InvalidDataException {
		if (view.contains(MMOKeys.COMMANDS.getQuery())) { return Optional.of(NPCCommandAction.build(view)); }
		else if (view.contains(MMOKeys.MESSAGES.getQuery())) { return Optional.of(NPCTalkAction.build(view)); }
		else throw new InvalidDataException();
	}
}