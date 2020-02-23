package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

public class PropertyMainHand extends PropertyType<ItemStack> {

	public PropertyMainHand() {
		super("MainHand", "mainhand", "equipment.mainhand");
	}

	@Override
	public TypeToken<ItemStack> getTypeToken() {
		return TypeToken.of(ItemStack.class);
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof ArmorEquipable;
	}

	@Override
	public void apply(NPCAble npc, ItemStack value) {
		((ArmorEquipable) npc).setItemInHand(HandTypes.MAIN_HAND, value);
	}
}
