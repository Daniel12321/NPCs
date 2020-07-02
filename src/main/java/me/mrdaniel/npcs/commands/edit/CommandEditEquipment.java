package me.mrdaniel.npcs.commands.edit;

import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.NPCCommand;
import me.mrdaniel.npcs.events.NPCEditEvent;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.gui.chat.npc.PropertiesChatMenu;
import me.mrdaniel.npcs.mixin.interfaces.NPCAble;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandEditEquipment extends NPCCommand {

	private final PropertyType<ItemStack> property;
	private final boolean remove;

	public CommandEditEquipment(PropertyType<ItemStack> property, boolean remove) {
		super(PropertiesChatMenu::new, false);

		this.property = property;
		this.remove = remove;
	}

	@Override
	public void execute(Player p, INPCData data, NPCAble npc, CommandContext args) throws CommandException {
		if (!this.property.isSupported(data.getProperty(PropertyTypes.TYPE).get())) {
			throw new CommandException(Text.of(TextColors.RED, "The selected NPC can not wear equipment!"));
		}
		if (Sponge.getEventManager().post(new NPCEditEvent(p, data, npc))) {
			throw new CommandException(Text.of(TextColors.RED, "Could not edit NPC: Event was cancelled!"));
		}

		ItemStack hand = this.remove ? null : p.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "You must be holding an item!"))).copy();
		npc.setProperty(this.property, hand).save();
	}

	public CommandSpec build() {
			return CommandSpec.builder()
					.description(Text.of(TextColors.GOLD, "NPCs | " + (this.remove ? "Remove " : "Give ") + this.property.getName()))
					.permission("npc.edit.equipment." + this.property.getId())
					.executor(this)
					.build();
		}
}
