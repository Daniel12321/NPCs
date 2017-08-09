package me.mrdaniel.npcs.commands.armor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.NPCFile;

public abstract class CommandEquipmentGive extends NPCCommand {

	public CommandEquipmentGive() {
		super(PageTypes.ARMOR);
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) { throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!")); }
		ArmorEquipable ae = (ArmorEquipable) npc;

		ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item.")));

		if (new NPCEvent.Edit(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		this.set(ae, hand);
		this.set(npc.getNPCFile(), hand);
	}

	public abstract void set(@Nonnull final ArmorEquipable ae, @Nullable final ItemStack stack);
	public abstract void set(@Nonnull final NPCFile file, @Nullable final ItemStack stack);

	public static class Helmet extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setHelmet(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setHelmet(stack).save(); }
	}

	public static class Chestplate extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setChestplate(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setChestplate(stack).save(); }
	}

	public static class Leggings extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setLeggings(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setLeggings(stack).save(); }
	}

	public static class Boots extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setBoots(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setBoots(stack).save(); }
	}

	public static class MainHand extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setItemInHand(HandTypes.MAIN_HAND, stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setMainHand(stack).save(); }
	}

	public static class OffHand extends CommandEquipmentGive {
		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setItemInHand(HandTypes.OFF_HAND, stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setOffHand(stack).save(); }
	}
}