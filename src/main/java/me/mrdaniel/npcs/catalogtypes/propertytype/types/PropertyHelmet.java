package me.mrdaniel.npcs.catalogtypes.propertytype.types;

import com.google.common.reflect.TypeToken;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

public class PropertyHelmet extends PropertyType<ItemStack> {

	public PropertyHelmet() {
		super("Helmet", "helmet", new Object[]{"equipment", "helmet"});
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
	public boolean isSupported(NPCType type) {
		return type.isArmorEquipable();
	}

	@Override
	public void apply(NPCAble npc, ItemStack value) {
		((ArmorEquipable) npc).setHelmet(value);
	}
}
