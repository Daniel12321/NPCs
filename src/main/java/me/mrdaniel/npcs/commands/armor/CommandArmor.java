package me.mrdaniel.npcs.commands.armor;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
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

public class CommandArmor extends NPCCommand {

	private final EquipmentType type;

	public CommandArmor(@Nonnull final NPCs npcs, @Nonnull final EquipmentType type) {
		super(npcs);

		this.type = type;
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) { throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!")); }
		ArmorEquipable ae = (ArmorEquipable) npc;

		Optional<ItemStack> hand = player.getItemInHand(HandTypes.MAIN_HAND);
		Optional<ItemStack> armor = ArmorUtils.get(ae, this.type);

		if (!armor.isPresent() && !hand.isPresent()) { throw new CommandException(Text.of(TextColors.RED, "You must be holding an item.")); }

		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		ArmorUtils.set(ae, this.type, hand.orElse(null));
		player.setItemInHand(HandTypes.MAIN_HAND, armor.orElse(null));
	}
}