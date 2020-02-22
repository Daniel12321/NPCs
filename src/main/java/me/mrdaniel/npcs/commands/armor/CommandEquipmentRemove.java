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

import java.util.Optional;

public abstract class CommandEquipmentRemove extends NPCCommand {

	// TODO: Rework to one class with types
	public CommandEquipmentRemove() {
		super(PageTypes.ARMOR);
	}

	@Override
	public void execute(Player p, NPCAble npc, CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) {
			throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!"));
		}
		ArmorEquipable ae = (ArmorEquipable) npc;
		this.get(ae).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "The selected NPC does not have that piece of equipment.")));

		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		this.clear(ae);
		this.clear(npc.getNPCData());
	}

	public abstract Optional<ItemStack> get(ArmorEquipable ae);
	public abstract void clear(ArmorEquipable ae);
	public abstract void clear(INPCData file);

	public static class Helmet extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getHelmet(); }
		@Override public void clear(ArmorEquipable ae) { ae.setHelmet(null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.HELMET, null).save(); }
	}

	public static class Chestplate extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getChestplate(); }
		@Override public void clear(ArmorEquipable ae) { ae.setChestplate(null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.CHARGED, null).save(); }
	}

	public static class Leggings extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getLeggings(); }
		@Override public void clear(ArmorEquipable ae) { ae.setLeggings(null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.LEGGINGS, null).save(); }
	}

	public static class Boots extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getBoots(); }
		@Override public void clear(ArmorEquipable ae) { ae.setBoots(null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.BOOTS, null).save(); }
	}

	public static class MainHand extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getItemInHand(HandTypes.MAIN_HAND); }
		@Override public void clear(ArmorEquipable ae) { ae.setItemInHand(HandTypes.MAIN_HAND, null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.MAINHAND, null).save(); }
	}

	public static class OffHand extends CommandEquipmentRemove {
		@Override public Optional<ItemStack> get(ArmorEquipable ae) { return ae.getItemInHand(HandTypes.OFF_HAND); }
		@Override public void clear(ArmorEquipable ae) { ae.setItemInHand(HandTypes.OFF_HAND, null); }
		@Override public void clear(INPCData file) { file.setProperty(PropertyTypes.OFFHAND, null).save(); }
	}
}
