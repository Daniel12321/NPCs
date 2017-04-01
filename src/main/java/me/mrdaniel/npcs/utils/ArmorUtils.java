package me.mrdaniel.npcs.utils;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;

public class ArmorUtils {

	public static String name(@Nonnull final EquipmentType type) {
		return type == EquipmentTypes.HEADWEAR ? "Helmet" : type == EquipmentTypes.CHESTPLATE ? "Chestplate" :type == EquipmentTypes.LEGGINGS ? "Leggings" : "Boots";
	}

	public static String name(@Nonnull final HandType type) {
		return type == HandTypes.MAIN_HAND ? "Main Hand" : "Off Hand";
	}

	public static Optional<ItemStack> get(@Nonnull final ArmorEquipable ae, @Nonnull final EquipmentType type) {
		return type == EquipmentTypes.HEADWEAR ? ae.getHelmet() : type == EquipmentTypes.LEGGINGS ? ae.getLeggings() : type == EquipmentTypes.CHESTPLATE ? ae.getChestplate() : ae.getBoots();
	}

	public static void set(@Nonnull final ArmorEquipable ae, @Nonnull final EquipmentType type, @Nullable final ItemStack item) {
		if (type == EquipmentTypes.HEADWEAR) { ae.setHelmet(item); }
		else if (type == EquipmentTypes.CHESTPLATE) { ae.setChestplate(item); }
		else if (type == EquipmentTypes.LEGGINGS) { ae.setLeggings(item); }
		else { ae.setBoots(item); }
	}
}