package me.mrdaniel.npcs.commands.edit;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.utils.ArmorUtils;
import me.mrdaniel.npcs.utils.TextUtils;

public class CommandHand extends NPCCommand {

	private final HandType type;

	public CommandHand(@Nonnull final NPCs npcs, @Nonnull final HandType type) {
		super(npcs);

		this.type = type;
	}

	@Override
	public void execute(final Player player, final Living npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) { throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not hold items!")); }
		ArmorEquipable ae = (ArmorEquipable) npc;

		Optional<ItemStack> phand = player.getItemInHand(HandTypes.MAIN_HAND);
		Optional<ItemStack> hand = ae.getItemInHand(this.type);

		if (!phand.isPresent() && !hand.isPresent()) { throw new CommandException(Text.of(TextColors.RED, "You must be holding an item.")); }

		if (super.getGame().getEventManager().post(new NPCEvent.Edit(super.getContainer(), player, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}
		ae.setItemInHand(this.type, phand.orElse(null));
		player.setItemInHand(HandTypes.MAIN_HAND, hand.orElse(null));

		player.sendMessage(TextUtils.getMessage("You successfully changed the selected NPC's ", ArmorUtils.name(this.type), "."));
	}
}