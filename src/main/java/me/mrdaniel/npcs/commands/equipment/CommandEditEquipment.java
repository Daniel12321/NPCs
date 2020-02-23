package me.mrdaniel.npcs.commands.armor;

import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandEditEquipment extends NPCCommand {

	private final PropertyType<ItemStack> property;
	private final boolean remove;

	public CommandEditEquipment(PropertyType<ItemStack> property, boolean remove) {
		super(PageTypes.ARMOR);

		this.property = property;
		this.remove = remove;
	}

	@Override
	public void execute(final Player p, final NPCAble npc, final CommandContext args) throws CommandException {
		if (!(npc instanceof ArmorEquipable)) {
			throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear armor!"));
		}

		ArmorEquipable ae = (ArmorEquipable) npc;
		ItemStack hand = this.remove ? null : p.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item.")));

		if (new NPCEditEvent(p, npc).post()) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		npc.setNPCProperty(this.property, hand);
	}

	public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | Give " + this.property.getName()))
					.permission("npc.armor." + this.property.getId() + ".give")
					.executor(this)
					.build();
		}
}
