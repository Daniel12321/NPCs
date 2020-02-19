package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class OptionChestplate extends OptionType<ItemStack> {

	public OptionChestplate() {
		super("Chestplate", "chestplate");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof ArmorEquipable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ItemStack value) {
		npc.setNPCChestplate(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ItemStack value) {
		file.setChestplate(value).save();
	}

	@Override
	public Optional<ItemStack> readFromFile(final NPCFile file) {
		return file.getChestplate();
	}
}
