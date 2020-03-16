package me.mrdaniel.npcs.actions.conditions;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypes;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ConditionItem extends Condition {

	private final ItemStack item;
	private final int amount;

	public ConditionItem(ConfigurationNode node) {
		super(ConditionTypes.ITEM);

		ItemStack is;
		try {
			is = node.getNode("item").getValue(TypeToken.of(ItemStack.class));
		} catch (ObjectMappingException exc) {
			NPCs.getInstance().getLogger().error("Failed to deserialize item", exc);
			is = ItemStack.builder().itemType(ItemTypes.AIR).build();
		}

		this.item = is;
		this.amount = node.getNode("amount").getInt(1);
	}

	public ConditionItem(ItemStack item, int amount) {
		super(ConditionTypes.ITEM);

		this.item = item;
		this.amount = amount;
	}

	@Override
	public boolean isMet(Player p) {
		int count = 0;

		System.out.println("Looking through inventory: ");
		for (Inventory slot : p.getInventory().query(QueryOperationTypes.ITEM_STACK_IGNORE_QUANTITY.of(this.item)).slots()) {
			System.out.println("  Slot: " + slot.toString());
			count += slot.peek().get().getQuantity();
		}

		return count >= this.amount;
	}

	@Override
	public void take(Player p) {
		int take = this.amount;

		for (Inventory slot : p.getInventory().query(QueryOperationTypes.ITEM_STACK_IGNORE_QUANTITY.of(this.item))) {
			ItemStack stack = slot.peek().get();
			if (stack.getQuantity() < take) {
				take -= stack.getQuantity();
				slot.clear();
			} else if (stack.getQuantity() == take) {
				slot.clear();
				return;
			} else {
				stack.setQuantity(stack.getQuantity() - take);
				slot.set(stack);
				return;
			}
		}
	}

	@Override
	public void serializeValue(ConfigurationNode node) throws ObjectMappingException {
		node.getNode("item").setValue(TypeToken.of(ItemStack.class), this.item);
		node.getNode("amount").setValue(this.amount);
	}

	@Override
	public Text getLine() {
		return Text.of(TextColors.GOLD, "Item: ", TextColors.AQUA, this.amount, "x ", this.item.get(Keys.DISPLAY_NAME).orElse(Text.of(this.item.getType().getName())));
	}
}
