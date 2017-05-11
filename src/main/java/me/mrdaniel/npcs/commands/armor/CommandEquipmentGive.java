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

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEvent;
import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.managers.menu.NPCMenu;

public abstract class CommandEquipmentGive extends NPCCommand {

	public CommandEquipmentGive(@Nonnull final NPCs npcs) {
		super(npcs, PageTypes.ARMOR);
	}

	@Override
	public void execute(final Player p, final NPCMenu menu, final CommandContext args) throws CommandException {
		if (!(menu.getNPC() instanceof ArmorEquipable)) { throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!")); }
		ArmorEquipable ae = (ArmorEquipable) menu.getNPC();

		ItemStack hand = p.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item.")));

		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), p, menu.getNPC(), menu.getFile()))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		this.set(ae, hand);
		this.set(menu.getFile(), hand);
		menu.getFile().save();
	}

	public abstract void set(@Nonnull final ArmorEquipable ae, @Nullable final ItemStack stack);
	public abstract void set(@Nonnull final NPCFile file, @Nullable final ItemStack stack);

	public static class Helmet extends CommandEquipmentGive {
		public Helmet(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setHelmet(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setHelmet(stack); }
	}

	public static class Chestplate extends CommandEquipmentGive {
		public Chestplate(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setChestplate(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setChestplate(stack); }
	}

	public static class Leggings extends CommandEquipmentGive {
		public Leggings(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setLeggings(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setLeggings(stack); }
	}

	public static class Boots extends CommandEquipmentGive {
		public Boots(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setBoots(stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setBoots(stack); }
	}

	public static class MainHand extends CommandEquipmentGive {
		public MainHand(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setItemInHand(HandTypes.MAIN_HAND, stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setMainHand(stack); }
	}

	public static class OffHand extends CommandEquipmentGive {
		public OffHand(@Nonnull final NPCs npcs) {
			super(npcs);
		}

		@Override public void set(final ArmorEquipable ae, final ItemStack stack) { ae.setItemInHand(HandTypes.OFF_HAND, stack); }
		@Override public void set(NPCFile file, ItemStack stack) { file.setOffHand(stack); }
	}
}