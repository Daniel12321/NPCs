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
import me.mrdaniel.npcs.commands.ai.*;
import me.mrdaniel.npcs.commands.edit.CommandEdit;
import me.mrdaniel.npcs.commands.edit.CommandEditEquipment;
import me.mrdaniel.npcs.commands.edit.CommandMove;
import me.mrdaniel.npcs.gui.chat.info.InfoMenu;
import me.mrdaniel.npcs.gui.chat.npc.AIPage;
import me.mrdaniel.npcs.gui.chat.npc.NPCChatMenu;
import me.mrdaniel.npcs.gui.chat.npc.PropertiesPage;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandNPC extends PlayerCommand {

	@Override
	public void execute(Player p, CommandContext args) throws CommandException {
		int id = args.<Integer>getOne("id").orElse(0);
		NPCChatMenu menu = NPCs.getInstance().getSelectedManager().get(p.getUniqueId()).orElse(null);

		if (id != 0) {
			INPCData data = NPCs.getInstance().getNPCManager().getData(id).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "No NPC with that ID exists!")));
			menu = NPCs.getInstance().getSelectedManager().getOrCreate(p, data);
			menu.setPlayer(p);
			menu.open();
		} else if (menu != null) {
			menu.setPlayer(p);
			menu.open();
		} else {
			new InfoMenu(p).open();
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
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.TYPE))
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.NAME), "name")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.NAME_VISIBLE), "namevisible", "name-visible")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SKIN), "skin", "skinname", "skin-name")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SKIN_UUID), "skinuuid", "skin-uuid")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.INTERACT), "interact")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SILENT), "silent")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.BURNING), "burning", "burn")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.GLOWING), "glowing", "glow")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.GLOWCOLOR), "glowcolor")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.ANGRY), "angry")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.ARMORED), "armored")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.BABY), "baby")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.CHARGED), "charged")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.CHEST), "chest")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.EATING), "eating")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.HANGING), "hanging", "hang")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.PEEKING), "peek", "peeking")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.PUMPKIN), "pumpkin")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SCREAMING), "screaming")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SHEARED), "sheared")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SITTING), "sitting", "sit")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SADDLE), "saddle", "saddled")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.SIZE), "size")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.CARRIES), "carries")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.CAREER), "career")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.COLOR), "color")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.HORSEARMOR), "horsearmor")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.HORSEPATTERN), "horsepattern")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.HORSECOLOR), "horsecolor")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.LLAMATYPE), "llamatype")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.CATTYPE), "cattype")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.RABBITTYPE), "rabbittype")
				.child(CommandEdit.build(PropertiesPage.class, PropertyTypes.PARROTTYPE), "parrottype")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | AI"))
						.child(CommandEdit.build(AIPage.class, PropertyTypes.LOOKING), "looking", "look")
						.child(CommandEdit.build(AIPage.class, PropertyTypes.TYPE))
						.child(new CommandWanderDistance().build(), "wanderdistance")
						.child(new CommandSpeed().build(), "speed")
						.child(new CommandChance().build(), "chance")
						.child(CommandSpec.builder()
								.child(new CommandPositionAdd().build(), "add")
								.child(new CommandPositionRemove().build(), "remove")
								.child(new CommandPositionSwap().build(), "swap")
								.build(), "position")
						.build(), "ai")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Helmet"))
						.child(new CommandEditEquipment(PropertyTypes.HELMET, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.HELMET, true).build(), "remove")
						.build(), "helmet", "helm")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Chestplate"))
						.child(new CommandEditEquipment(PropertyTypes.CHESTPLATE, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.CHESTPLATE, true).build(), "remove")
						.build(), "chestplate", "chest")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Leggings"))
						.child(new CommandEditEquipment(PropertyTypes.LEGGINGS, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.LEGGINGS, true).build(), "remove")
						.build(), "leggings", "legs")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Boots"))
						.child(new CommandEditEquipment(PropertyTypes.BOOTS, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.BOOTS, true).build(), "remove")
						.build(), "boots", "feet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | MainHand"))
						.child(new CommandEditEquipment(PropertyTypes.MAINHAND, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.MAINHAND, true).build(), "remove")
						.build(), "mainhand", "main-hand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | OffHand"))
						.child(new CommandEditEquipment(PropertyTypes.OFFHAND, false).build(), "give")
						.child(new CommandEditEquipment(PropertyTypes.OFFHAND, true).build(), "remove")
						.build(), "offhand", "off-hand")
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
