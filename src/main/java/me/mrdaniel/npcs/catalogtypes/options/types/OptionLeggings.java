package me.mrdaniel.npcs.catalogtypes.options.types;

import java.util.Optional;

import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionLeggings extends OptionType<ItemStack> {

	public OptionLeggings() {
		super("Leggings", "leggings");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof ArmorEquipable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ItemStack value) {
		npc.setNPCLeggings(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ItemStack value) {
		file.setLeggings(value);
	}

	@Override
	public Optional<ItemStack> readFromFile(final NPCFile file) {
		return file.getLeggings();
	}
}