package me.mrdaniel.npcs.actions.conditions;

import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypes;
import me.mrdaniel.npcs.utils.TextUtils;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.Optional;

public class ConditionItem extends Condition {

	private final ItemType type;
	private final int amount;
	@Nullable private final String name;

	public ConditionItem(ConfigurationNode node) {
		this(node.getNode("ItemType").getValue(obj -> Sponge.getRegistry().getType(ItemType.class, (String)obj)).get(),
				node.getNode("Amount").getInt(1),
				node.getNode("Name").getString(null));
	}

	public ConditionItem(ItemType type, int amount, @Nullable String name) {
		super(ConditionTypes.ITEM);

		this.type = type;
		this.amount = amount;
		this.name = name;
	}

	@Override
	public boolean isMet(Player p) {
		int i = 0;
		for (Inventory s : p.getInventory().slots()) {
			ItemStack stack = s.peek().orElse(null);
			if (stack != null && stack.getType() == this.type) {
				if (this.name != null && !this.name.equalsIgnoreCase(stack.get(Keys.DISPLAY_NAME).map(TextUtils::toString).orElse(""))) {
					continue;
				}
				i += stack.getQuantity();
				if (i >= this.amount) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void take(Player p) {
		int take = this.amount;
		for (Inventory s : p.getInventory().slots()) {
			ItemStack stack = s.peek().orElse(null);
			if (stack != null && stack.getType() == this.type) {
				if (stack.getQuantity() < take) {
					take -= stack.getQuantity();
					s.clear();
				} else if (stack.getQuantity() == take) {
					s.clear();
					return;
				} else {
					stack.setQuantity(stack.getQuantity() - take);
					s.set(stack);
					return;
				}
			}
		}
	}

	@Override
	public void serializeValue(ConfigurationNode node) {
		node.getNode("ItemType").setValue(this.type.getId());
		node.getNode("Amount").setValue(this.amount);

		if (this.name != null) {
			node.getNode("Name").setValue(this.name);
		}
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Item: ", TextColors.AQUA, this.amount, "x ", Optional.ofNullable(this.name).orElse(this.type.getName()));
	}
}
