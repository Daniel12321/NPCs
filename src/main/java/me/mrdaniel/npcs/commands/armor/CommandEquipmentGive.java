package me.mrdaniel.npcs.commands.armor;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;

public abstract class CommandEquipmentGive extends NPCCommand {

	// TODO: Rework to one class with types
	public CommandEquipmentGive() {
		super(PageTypes.ARMOR);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) {
			throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!"));
		}

		ArmorEquipable ae = (ArmorEquipable) npc;
		ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item.")));

		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		this.set(ae, hand);
		this.set(npc.getNPCData(), hand);
	}

	public abstract void set(ArmorEquipable ae, @Nullable ItemStack stack);
	public abstract void set(INPCData file, @Nullable ItemStack stack);

	public static class Helmet extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setHelmet(stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.HELMET, stack).save(); }
	}

	public static class Chestplate extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setChestplate(stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.CHESTPLATE, stack).save(); }
	}

	public static class Leggings extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setLeggings(stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.LEGGINGS, stack).save(); }
	}

	public static class Boots extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setBoots(stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.BOOTS, stack).save(); }
	}

	public static class MainHand extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setItemInHand(HandTypes.MAIN_HAND, stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.MAINHAND, stack).save(); }
	}

	public static class OffHand extends CommandEquipmentGive {
		@Override public void set(ArmorEquipable ae, ItemStack stack) { ae.setItemInHand(HandTypes.OFF_HAND, stack); }
		@Override public void set(INPCData file, ItemStack stack) { file.setProperty(PropertyTypes.OFFHAND, stack).save(); }
	}
}
