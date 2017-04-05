package me.mrdaniel.npcs.data.action;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.utils.TextUtils;

public class Action implements DataSerializable {

	private final ActionType type;
	private final String value;

	public Action(@Nonnull final ActionType type, @Nonnull final String value) {
		this.type = type;
		this.value = value;
	}

	@Nonnull public String getValue() { return this.value; }
	@Nonnull public ActionType getType() { return this.type; }

	public void execute(@Nonnull final NPCs npcs, @Nonnull final Player p) {
		String value = npcs.getPlaceHolderManager().format(p, this.value);

		if (this.type == ActionType.MESSAGE) { p.sendMessage(TextUtils.toText(value)); }
		else if (this.type == ActionType.PLAYERCMD) { npcs.getGame().getCommandManager().process(p, value); }
		else { npcs.getGame().getCommandManager().process(npcs.getGame().getServer().getConsole(), value); }
	}

	@Override public int getContentVersion() { return 1; }

	@Override
	public DataContainer toContainer() {
		return new MemoryDataContainer()
				.set(Queries.CONTENT_VERSION, 1)
				.set(DataQuery.of("action_type"), this.type.getId())
				.set(DataQuery.of("action_value"), this.value);
	}
}