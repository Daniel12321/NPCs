package me.mrdaniel.npcs.data.npc.actions.conditions;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;

public class Condition implements DataSerializable {

	private final ConditionType type;

	public Condition(@Nonnull final ConditionType type) {
		this.type = type;
	}

	public boolean isMet(@Nonnull final Player p) {
		return type == ConditionType.EXP ?
				p.get(Keys.EXPERIENCE_LEVEL).orElse(0) > 10 : 
				p.getInventory().query(ItemTypes.EMERALD).peek().isPresent();
	}

	@Override
	public DataContainer toContainer() {
		return new MemoryDataContainer()
				.set(DataQuery.of("ContentVersion"), this.getContentVersion())
				.set(DataQuery.of("condition_type"), this.type.getId());
	}

	@Override public int getContentVersion() { return 1; }

	public void apply() {
		;
	}
}