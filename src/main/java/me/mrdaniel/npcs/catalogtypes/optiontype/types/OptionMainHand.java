package me.mrdaniel.npcs.catalogtypes.optiontype.types;

import java.util.Optional;

import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;

import me.mrdaniel.npcs.catalogtypes.optiontype.OptionType;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public class OptionMainHand extends OptionType<ItemStack> {

	public OptionMainHand() {
		super("MainHand", "mainhand");
	}

	@Override
	public boolean isSupported(final NPCAble npc) {
		return npc instanceof ArmorEquipable;
	}

	@Override
	public void writeToNPC(final NPCAble npc, final ItemStack value) {
		npc.setNPCMainHand(value);
	}

	@Override
	public void writeToFile(final NPCFile file, final ItemStack value) {
		file.setMainHand(value).save();
	}

	@Override
	public Optional<ItemStack> readFromFile(final NPCFile file) {
		return file.getMainHand();
	}
}