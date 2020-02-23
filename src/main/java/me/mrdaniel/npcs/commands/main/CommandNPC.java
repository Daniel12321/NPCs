package me.mrdaniel.npcs.commands.main;

import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.commands.PlayerCommand;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import me.mrdaniel.npcs.commands.action.CommandActionRemove;
import me.mrdaniel.npcs.commands.action.CommandActionRepeat;
import me.mrdaniel.npcs.commands.action.CommandActionSwap;
import me.mrdaniel.npcs.commands.action.condition.CommandActionAddCondition;
import me.mrdaniel.npcs.commands.action.edit.*;
import me.mrdaniel.npcs.commands.equipment.CommandEditEquipment;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.menu.chat.npc.PropertiesChatMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class CommandNPC extends PlayerCommand {

	private final Text[] lines;

	public CommandNPC() {
		this.lines = new Text[]{
				Text.EMPTY,
				Text.of(TextColors.YELLOW, "---------------=====[ ", TextColors.RED, "NPC Info", TextColors.YELLOW, " ]=====---------------"),
				Text.of(TextColors.AQUA, "You currently have not selected an NPC."),
				Text.of(TextColors.AQUA, "You can select an NPC by shift right clicking it."),
				Text.of(TextColors.AQUA, "You can see a list of NPC's by running: ", TextColors.YELLOW, "/npc list"),
				Text.of(TextColors.AQUA, "You can create an NPC by running: ", TextColors.YELLOW, "/npc create ", TextColors.GOLD, "[entitytype]"),
				Text.of(TextColors.YELLOW, "--------------------------------------------------")
		};
	}

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		Optional<INPCData> selected = NPCs.getInstance().getMenuManager().get(p.getUniqueId());

		if (selected.isPresent()) {
			NPCAble npc = NPCs.getInstance().getNPCManager().getNPC(selected.get()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "NPC is not spawned in!")));
			new PropertiesChatMenu(npc).send(p);
		} else {
			p.sendMessages(this.lines);
		}
	}

	public CommandSpec build() {
		return CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Main Command"))
				.executor(this)
				.child(new CommandReload().build(), "reload")
				.child(new CommandList().build(), "list")
				.child(new CommandCreate().build(), "create")
				.child(new CommandRemove().build(), "remove")
				.child(new CommandCopy().build(), "copy")
				.child(new CommandMount().build(), "mount")
				.child(new CommandGoto().build(), "goto")
				.child(new CommandMove().build(), "move")
				.child(new CommandRespawn().build(), "respawn")
				.child(new CommandDeselect().build(), "deselect")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.NAME), "name")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.NAME_VISIBLE), "namevisible")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SKIN), "skin")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SKIN_UUID), "skinuuid")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.LOOKING), "looking")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.INTERACT), "interact")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.GLOWING), "glowing")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SILENT), "silent")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.GLOWCOLOR), "glowcolor")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.BABY), "baby")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.CHARGED), "charged")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.ANGRY), "angry")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SIZE), "size")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SITTING), "sitting")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.SADDLE), "saddle")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.HANGING), "hanging")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.PUMPKIN), "pumpkin")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.CAREER), "career")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.HORSEPATTERN), "horsepattern")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.HORSECOLOR), "horsecolor")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.LLAMATYPE), "llamatype")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.CATTYPE), "cattype")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.RABBITTYPE), "rabbittype")
				.child(CommandEdit.build(PropertiesChatMenu::new, PropertyTypes.PARROTTYPE), "parrottype")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Helmet"))
						.child(new CommandEditEquipment(PropertyTypes.HELMET, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.HELMET, true).build(), "remove")
						.build(), "helmet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Chestplate"))
						.child(new CommandEditEquipment(PropertyTypes.CHESTPLATE, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.CHESTPLATE, true).build(), "remove")
						.build(), "chestplate")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Leggings"))
						.child(new CommandEditEquipment(PropertyTypes.LEGGINGS, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.LEGGINGS, true).build(), "remove")
						.build(), "leggings")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Boots"))
						.child(new CommandEditEquipment(PropertyTypes.BOOTS, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.BOOTS, true).build(), "remove")
						.build(), "boots")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | MainHand"))
						.child(new CommandEditEquipment(PropertyTypes.MAINHAND, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.MAINHAND, true).build(), "remove")
						.build(), "mainhand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | OffHand"))
						.child(new CommandEditEquipment(PropertyTypes.OFFHAND, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.OFFHAND, true).build(), "remove")
						.build(), "offhand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Actions"))
						.child(new CommandActionRepeat().build(), "repeat")
						.child(new CommandActionSwap().build(), "swap")
						.child(new CommandActionRemove().build(), "remove")
						.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Add Action"))
								.child(new CommandActionAdd.PlayerCommand().build(), "playercmd")
								.child(new CommandActionAdd.ConsoleCommand().build(), "consolecmd")
								.child(new CommandActionAdd.Message().build(), "message")
								.child(new CommandActionAdd.Delay().build(), "delay")
								.child(new CommandActionAdd.Cooldown().build(), "cooldown")
								.child(new CommandActionAdd.Pause().build(), "pause")
								.child(new CommandActionAdd.Goto().build(), "goto")
								.child(new CommandActionAdd.Choices().build(), "choices")
								.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "Add Condition Action"))
										.child(new CommandActionAddCondition.Item().build(), "item")
										.child(new CommandActionAddCondition.Money().build(), "money")
										.child(new CommandActionAddCondition.Level().build(), "level")
										.child(new CommandActionAddCondition.Exp().build(), "exp")
										.build(), "condition")
								.build(), "add")
						.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Edit Action")).arguments(GenericArguments.integer(Text.of("index")))
								.child(new CommandSetConsoleCommand().build(), "consolecmd")
								.child(new CommandSetPlayerCommand().build(), "playercmd")
								.child(new CommandSetMessage().build(), "message")
								.child(new CommandSetGoto().build(), "goto")
								.child(new CommandSetDelay().build(), "delay")
								.child(new CommandSetCooldownTime().build(), "cooldowntime")
								.child(new CommandSetCooldownMessage().build(), "cooldownmessage")
								.child(new CommandSetTake().build(), "take")
								.child(new CommandSetGotoFailed().build(), "goto_failed")
								.child(new CommandSetGotoMet().build(), "goto_met")
								.child(new CommandAddChoice().build(), "addchoice", "setchoice")
								.child(new CommandRemoveChoice().build(), "removechoice")
								.build(), "edit")
						.build(), "action", "actions")
				.build();
	}
}
