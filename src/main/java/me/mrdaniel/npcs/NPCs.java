package me.mrdaniel.npcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;

import lombok.Getter;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionTypeSerializer;
import me.mrdaniel.npcs.actions.conditions.Condition;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;
import me.mrdaniel.npcs.actions.conditions.ConditionTypeSerializer;
import me.mrdaniel.npcs.bstats.MetricsLite;
import me.mrdaniel.npcs.catalogtypes.actions.ActionType;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.conditions.ConditionType;
import me.mrdaniel.npcs.catalogtypes.conditions.ConditionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.menupages.PageType;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.menupages.PageTypes;
import me.mrdaniel.npcs.catalogtypes.options.OptionType;
import me.mrdaniel.npcs.catalogtypes.options.OptionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.options.OptionTypes;
import me.mrdaniel.npcs.commands.CommandEdit;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import me.mrdaniel.npcs.commands.action.CommandActionRemove;
import me.mrdaniel.npcs.commands.action.CommandActionRepeat;
import me.mrdaniel.npcs.commands.action.CommandActionSwap;
import me.mrdaniel.npcs.commands.action.condition.CommandActionAddCondition;
import me.mrdaniel.npcs.commands.action.edit.CommandAddChoice;
import me.mrdaniel.npcs.commands.action.edit.CommandRemoveChoice;
import me.mrdaniel.npcs.commands.action.edit.CommandSetConsoleCommand;
import me.mrdaniel.npcs.commands.action.edit.CommandSetCooldownMessage;
import me.mrdaniel.npcs.commands.action.edit.CommandSetCooldownTime;
import me.mrdaniel.npcs.commands.action.edit.CommandSetDelay;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGoto;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGotoFailed;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGotoMet;
import me.mrdaniel.npcs.commands.action.edit.CommandSetMessage;
import me.mrdaniel.npcs.commands.action.edit.CommandSetPlayerCommand;
import me.mrdaniel.npcs.commands.action.edit.CommandSetTake;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentGive;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentRemove;
import me.mrdaniel.npcs.commands.main.CommandCopy;
import me.mrdaniel.npcs.commands.main.CommandCreate;
import me.mrdaniel.npcs.commands.main.CommandDeselect;
import me.mrdaniel.npcs.commands.main.CommandGoto;
import me.mrdaniel.npcs.commands.main.CommandInfo;
import me.mrdaniel.npcs.commands.main.CommandList;
import me.mrdaniel.npcs.commands.main.CommandMount;
import me.mrdaniel.npcs.commands.main.CommandMove;
import me.mrdaniel.npcs.commands.main.CommandRemove;
import me.mrdaniel.npcs.data.npc.ImmutableNPCData;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

@Plugin(id = "npcs",
		name = "NPCs",
		version = "2.1.0",
		authors = {"Daniel12321"},
		url = "https://github.com/Daniel12321/NPCs",
		description = "A plugin that adds simple custom NPC's to your worlds.",
		dependencies = { @Dependency(id = "placeholderapi", version = "[4.1,)", optional = true) })
public class NPCs {

	@Getter private static NPCs instance;

	@Getter private final Game game;
	@Getter private final PluginContainer container;
	@Getter private final Logger logger;
	@Getter private final Path configDir;
	@Getter private final Config config;

	@Inject
	public NPCs(final Game game, final PluginContainer container, @ConfigDir(sharedRoot = false) final Path path, final MetricsLite metrics) {
		instance = this;

		this.game = game;
		this.container = container;
		this.logger = LoggerFactory.getLogger("NPCs");
		this.configDir = path;
		this.config = new Config(this.configDir, "config.conf");

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { this.logger.error("Failed to create main config directory: {}", exc); }
		}
	}

	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
		DataRegistration.builder().dataClass(NPCData.class).immutableClass(ImmutableNPCData.class).builder(new NPCDataBuilder()).dataName("npc").manipulatorId("npc").buildAndRegister(this.container);
		this.game.getRegistry().registerModule(ActionType.class, ActionTypeRegistryModule.getInstance());
		this.game.getRegistry().registerModule(ConditionType.class, ConditionTypeRegistryModule.getInstance());
		this.game.getRegistry().registerModule(PageType.class, PageTypeRegistryModule.getInstance());
		this.game.getRegistry().registerModule(OptionType.class, OptionTypeRegistryModule.getInstance());

		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		this.game.getCommandManager().register(this, CommandSpec.builder().description(this.getDescription("Main Command"))
			.executor(new CommandInfo())
			.child(CommandSpec.builder().description(this.getDescription("List")).permission("npc.list").executor(CommandList.getInstance()).build(), "list")
			.child(CommandSpec.builder().description(this.getDescription("Create")).permission("npc.create").arguments(GenericArguments.optional(GenericArguments.catalogedElement(Text.of("type"), EntityType.class))).executor(new CommandCreate()).build(), "create")
			.child(CommandSpec.builder().description(this.getDescription("Remove")).permission("npc.remove").arguments(GenericArguments.optionalWeak(GenericArguments.integer(Text.of("id")))).executor(new CommandRemove()).build(), "remove")
			.child(CommandSpec.builder().description(this.getDescription("Copy")).permission("npc.copy").executor(new CommandCopy()).build(), "copy")
			.child(CommandSpec.builder().description(this.getDescription("Mount")).permission("npc.mount").executor(new CommandMount()).build(), "mount")
			.child(CommandSpec.builder().description(this.getDescription("Deselect")).permission("npc.deselect").executor(new CommandDeselect()).build(), "deselect")
			.child(CommandSpec.builder().description(this.getDescription("GoTo")).permission("npc.goto").executor(new CommandGoto()).build(), "goto")
			.child(CommandSpec.builder().description(this.getDescription("Move")).permission("npc.edit.move").executor(new CommandMove()).build(), "move")
			.child(CommandSpec.builder().description(this.getDescription("Set Name")).permission("npc.edit.name").arguments(GenericArguments.remainingJoinedStrings(Text.of("name"))).executor(new CommandEdit<String>(PageTypes.MAIN, OptionTypes.NAME)).build(), "name")
			.child(CommandSpec.builder().description(this.getDescription("Set Skin")).permission("npc.edit.skin").arguments(GenericArguments.string(Text.of("skin"))).executor(new CommandEdit<String>(PageTypes.MAIN, OptionTypes.SKIN)).build(), "skin")
			.child(CommandSpec.builder().description(this.getDescription("Set Looking")).permission("npc.edit.look").arguments(GenericArguments.bool(Text.of("looking"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.LOOKING)).build(), "looking")
			.child(CommandSpec.builder().description(this.getDescription("Set Interact")).permission("npc.edit.interact").arguments(GenericArguments.bool(Text.of("interact"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.INTERACT)).build(), "interact")
			.child(CommandSpec.builder().description(this.getDescription("Set Glowing")).permission("npc.edit.glowing").arguments(GenericArguments.bool(Text.of("glowing"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.GLOWING)).build(), "glowing")
			.child(CommandSpec.builder().description(this.getDescription("Set Silent")).permission("npc.edit.silent").arguments(GenericArguments.bool(Text.of("silent"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.SILENT)).build(), "silent")
			.child(CommandSpec.builder().description(this.getDescription("Set GlowColor")).permission("npc.edit.glowcolor").arguments(GenericArguments.catalogedElement(Text.of("glowcolor"), TextColor.class)).executor(new CommandEdit<TextColor>(PageTypes.MAIN, OptionTypes.GLOWCOLOR)).build(), "glowcolor")
			.child(CommandSpec.builder().description(this.getDescription("Set Baby")).permission("npc.edit.baby").arguments(GenericArguments.bool(Text.of("baby"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.BABY)).build(), "baby")
			.child(CommandSpec.builder().description(this.getDescription("Set Charged")).permission("npc.edit.charged").arguments(GenericArguments.bool(Text.of("charged"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.CHARGED)).build(), "charged")
			.child(CommandSpec.builder().description(this.getDescription("Set Angry")).permission("npc.edit.angry").arguments(GenericArguments.bool(Text.of("angry"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.ANGRY)).build(), "angry")
			.child(CommandSpec.builder().description(this.getDescription("Set Size")).permission("npc.edit.size").arguments(GenericArguments.integer(Text.of("size"))).executor(new CommandEdit<Integer>(PageTypes.MAIN, OptionTypes.SIZE)).build(), "size")
			.child(CommandSpec.builder().description(this.getDescription("Set Sitting")).permission("npc.edit.sitting").arguments(GenericArguments.bool(Text.of("sitting"))).executor(new CommandEdit<Boolean>(PageTypes.MAIN, OptionTypes.SITTING)).build(), "sitting")
			.child(CommandSpec.builder().description(this.getDescription("Set Career")).permission("npc.edit.career").arguments(GenericArguments.catalogedElement(Text.of("career"), Career.class)).executor(new CommandEdit<Career>(PageTypes.MAIN, OptionTypes.CAREER)).build(), "career")
			.child(CommandSpec.builder().description(this.getDescription("Set HorseStyle")).permission("npc.edit.horsestyle").arguments(GenericArguments.catalogedElement(Text.of("horsestyle"), HorseStyle.class)).executor(new CommandEdit<HorseStyle>(PageTypes.MAIN, OptionTypes.HORSESTYLE)).build(), "horsestyle")
			.child(CommandSpec.builder().description(this.getDescription("Set HorseColor")).permission("npc.edit.horsecolor").arguments(GenericArguments.catalogedElement(Text.of("horsecolor"), HorseColor.class)).executor(new CommandEdit<HorseColor>(PageTypes.MAIN, OptionTypes.HORSECOLOR)).build(), "horsecolor")
			.child(CommandSpec.builder().description(this.getDescription("Set LlamaVariant")).permission("npc.edit.llamavariant").arguments(GenericArguments.catalogedElement(Text.of("llamavariant"), LlamaVariant.class)).executor(new CommandEdit<LlamaVariant>(PageTypes.MAIN, OptionTypes.LLAMAVARIANT)).build(), "llamavariant")
			.child(CommandSpec.builder().description(this.getDescription("Set CatType")).permission("npc.edit.cattype").arguments(GenericArguments.catalogedElement(Text.of("cattype"), OcelotType.class)).executor(new CommandEdit<OcelotType>(PageTypes.MAIN, OptionTypes.CATTYPE)).build(), "cattype")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Helmet"))
				.child(CommandSpec.builder().description(this.getDescription("Give Helmet")).permission("npc.armor.helmet.give").executor(new CommandEquipmentGive.Helmet()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove Helmet")).permission("npc.armor.helmet.remove").executor(new CommandEquipmentRemove.Helmet()).build(), "remove")
				.build(), "helmet")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Chestplate"))
				.child(CommandSpec.builder().description(this.getDescription("Give Chestplate")).permission("npc.armor.chestplate.give").executor(new CommandEquipmentGive.Chestplate()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove Chestplate")).permission("npc.armor.chestplate.remove").executor(new CommandEquipmentRemove.Chestplate()).build(), "remove")
				.build(), "chestplate")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Leggings"))
				.child(CommandSpec.builder().description(this.getDescription("Give Leggings")).permission("npc.armor.leggings.give").executor(new CommandEquipmentGive.Leggings()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove Leggings")).permission("npc.armor.leggings.remove").executor(new CommandEquipmentRemove.Leggings()).build(), "remove")						
				.build(), "leggings")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Boots"))
				.child(CommandSpec.builder().description(this.getDescription("Give Boots")).permission("npc.armor.boots.give").executor(new CommandEquipmentGive.Boots()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove Boots")).permission("npc.armor.boots.remove").executor(new CommandEquipmentRemove.Boots()).build(), "remove")
				.build(), "boots")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | MainHand"))
				.child(CommandSpec.builder().description(this.getDescription("Give MainHand")).permission("npc.armor.mainhand.give").executor(new CommandEquipmentGive.MainHand()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove MainHand")).permission("npc.armor.mainhand.remove").executor(new CommandEquipmentRemove.MainHand()).build(), "remove")
				.build(), "mainhand")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | OffHand"))
				.child(CommandSpec.builder().description(this.getDescription("Give OffHand")).permission("npc.armor.offhand.give").executor(new CommandEquipmentGive.OffHand()).build(), "give")
				.child(CommandSpec.builder().description(this.getDescription("Remove OffHand")).permission("npc.armor.offhand.remove").executor(new CommandEquipmentRemove.OffHand()).build(), "remove")
				.build(), "offhand")
			.child(CommandSpec.builder().description(this.getDescription("Actions"))
				.child(CommandSpec.builder().description(this.getDescription("Set Repeat Actions")).permission("npc.action.repeat").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("repeat")))).executor(new CommandActionRepeat()).build(), "repeat")
				.child(CommandSpec.builder().description(this.getDescription("Swap Actions")).permission("npc.action.swap").arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second"))).executor(new CommandActionSwap()).build(), "swap")
				.child(CommandSpec.builder().description(this.getDescription("Remove Action")).permission("npc.action.remove").arguments(GenericArguments.integer(Text.of("number"))).executor(new CommandActionRemove()).build(), "remove")
				.child(CommandSpec.builder().description(this.getDescription("Add Action"))
					.child(CommandSpec.builder().description(this.getDescription("Add Player Command Action")).permission("npc.action.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.PlayerCommand()).build(), "playercmd")
					.child(CommandSpec.builder().description(this.getDescription("Add Console Command Action")).permission("npc.action.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.ConsoleCommand()).build(), "consolecmd")
					.child(CommandSpec.builder().description(this.getDescription("Add Message Action")).permission("npc.action.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandActionAdd.Message()).build(), "message")
					.child(CommandSpec.builder().description(this.getDescription("Add Delay Action")).permission("npc.action.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandActionAdd.Delay()).build(), "delay")
					.child(CommandSpec.builder().description(this.getDescription("Add Cooldown Action")).permission("npc.action.cooldown").arguments(GenericArguments.integer(Text.of("seconds")), GenericArguments.string(Text.of("message"))).executor(new CommandActionAdd.Cooldown()).build(), "cooldown")
					.child(CommandSpec.builder().description(this.getDescription("Add Pause Action")).permission("npc.action.pause").executor(new CommandActionAdd.Pause()).build(), "pause")
					.child(CommandSpec.builder().description(this.getDescription("Add Goto Action")).permission("npc.action.goto").arguments(GenericArguments.integer(Text.of("next"))).executor(new CommandActionAdd.Goto()).build(), "goto")
					.child(CommandSpec.builder().description(this.getDescription("Add Choices Action")).permission("npc.action.choices").arguments(GenericArguments.string(Text.of("first")), GenericArguments.integer(Text.of("goto_first")), GenericArguments.string(Text.of("second")), GenericArguments.integer(Text.of("goto_second"))).executor(new CommandActionAdd.Choices()).build(), "choices")
					.child(CommandSpec.builder().description(this.getDescription("Add Condition Action"))
						.child(CommandSpec.builder().description(this.getDescription("Add Item Condition Action")).permission("npc.action.condition.item").arguments(GenericArguments.catalogedElement(Text.of("type"), ItemType.class), GenericArguments.integer(Text.of("amount")), GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandActionAddCondition.Item()).build(), "item")
						.child(CommandSpec.builder().description(this.getDescription("Add Money Condition Action")).permission("npc.action.condition.money").arguments(GenericArguments.catalogedElement(Text.of("type"), Currency.class), GenericArguments.doubleNum(Text.of("amount"))).executor(new CommandActionAddCondition.Money()).build(), "money")
						.child(CommandSpec.builder().description(this.getDescription("Add Level Condition Action")).permission("npc.action.condition.level").arguments(GenericArguments.integer(Text.of("level"))).executor(new CommandActionAddCondition.Level()).build(), "level")
						.child(CommandSpec.builder().description(this.getDescription("Add Exp Condition Action")).permission("npc.action.condition.exp").arguments(GenericArguments.integer(Text.of("exp"))).executor(new CommandActionAddCondition.Exp()).build(), "exp")
						.build(), "condition")
					.build(), "add")
				.child(CommandSpec.builder().description(this.getDescription("Edit Action")).arguments(GenericArguments.integer(Text.of("index")))
					.child(CommandSpec.builder().description(this.getDescription("Set Console Command")).permission("npc.action.edit.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetConsoleCommand()).build(), "consolecmd")
					.child(CommandSpec.builder().description(this.getDescription("Set Player Command")).permission("npc.action.edit.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetPlayerCommand()).build(), "playercmd")
					.child(CommandSpec.builder().description(this.getDescription("Set Message")).permission("npc.action.edit.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandSetMessage()).build(), "message")
					.child(CommandSpec.builder().description(this.getDescription("Set Goto")).permission("npc.action.edit.goto").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGoto()).build(), "goto")
					.child(CommandSpec.builder().description(this.getDescription("Set Delay")).permission("npc.action.edit.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandSetDelay()).build(), "delay")
					.child(CommandSpec.builder().description(this.getDescription("Set Cooldown Time")).permission("npc.action.edit.cooldown.time").arguments(GenericArguments.integer(Text.of("seconds"))).executor(new CommandSetCooldownTime()).build(), "cooldowntime")
					.child(CommandSpec.builder().description(this.getDescription("Set Cooldown Message")).permission("npc.action.edit.cooldown.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandSetCooldownMessage()).build(), "cooldownmessage")
					.child(CommandSpec.builder().description(this.getDescription("Take")).permission("npc.action.edit.condition.take").arguments(GenericArguments.bool(Text.of("take"))).executor(new CommandSetTake()).build(), "take")
					.child(CommandSpec.builder().description(this.getDescription("Goto Failed")).permission("npc.action.edit.condition.goto.failed").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoFailed()).build(), "goto_failed")
					.child(CommandSpec.builder().description(this.getDescription("Goto Met")).permission("npc.action.edit.condition.goto.met").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoMet()).build(), "goto_met")
					.child(CommandSpec.builder().description(this.getDescription("Add Choice")).permission("npc.action.edit.choice.add").arguments(GenericArguments.string(Text.of("name")), GenericArguments.integer(Text.of("goto"))).executor(new CommandAddChoice()).build(), "addchoice", "setchoice")
					.child(CommandSpec.builder().description(this.getDescription("Remove Choice")).permission("npc.action.edit.choice.remove").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandRemoveChoice()).build(), "removechoice")
					.build(), "edit")
				.build(), "action", "actions")
			.build(), "npc", "npcs");

		this.logger.info("Loaded plugin successfully.");
	}

	private Text getDescription(@Nonnull final String str) {
		return Text.of(TextColors.GOLD, "NPCs | ", str);
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.logger.info("Unloading Plugin...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks().forEach(task -> task.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.logger.info("Unloaded plugin successfully.");

		this.onInit(null);
	}

	@Listener
	public void onServiceChange(final ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			ConditionMoney.setEconomyService((EconomyService) e.getNewProvider());
		}
	}

	@Listener(order = Order.LATE)
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		MenuManager.getInstance().deselect(e.getTargetEntity().getUniqueId());
		ActionManager.getInstance().removeChoosing(e.getTargetEntity().getUniqueId());
	}

	@Listener
	public void updateNPC(final SpawnEntityEvent.ChunkLoad e) {
		e.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> NPCManager.getInstance().getFile(data.getId()).ifPresent(file -> {
			NPCAble npc = (NPCAble) ent;
			ent.remove(NPCData.class);
			npc.setNPCFile(file);
		})));
	}
}