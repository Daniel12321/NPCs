package me.mrdaniel.npcs.commands.armor;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.ArmorUtils;

public class CommandTakeArmor extends NPCCommand {

	private final EquipmentType type;

	public CommandTakeArmor(@Nonnull final NPCs npcs, @Nonnull final EquipmentType type) {
		super(npcs);

		this.type = type;
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) { throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!")); }
		ArmorEquipable ae = (ArmorEquipable) npc;

		ItemStack armor = ArmorUtils.get(ae, this.type).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "The selected NPC is not wearing that armor piece.")));

		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		ArmorUtils.set(ae, this.type, null);
		player.getInventory().offer(armor);
	}
}