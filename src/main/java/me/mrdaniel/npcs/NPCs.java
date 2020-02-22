package me.mrdaniel.npcs;

import com.google.inject.Inject;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;
import me.mrdaniel.npcs.bstats.MetricsLite;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.career.CareerRegistryModule;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColor;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColorRegistryModule;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColor;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColorRegistryModule;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePattern;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatternRegistryModule;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaType;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageType;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.menupagetype.PageTypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypeRegistryModule;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import me.mrdaniel.npcs.commands.action.CommandActionRemove;
import me.mrdaniel.npcs.commands.action.CommandActionRepeat;
import me.mrdaniel.npcs.commands.action.CommandActionSwap;
import me.mrdaniel.npcs.commands.action.condition.CommandActionAddCondition;
import me.mrdaniel.npcs.commands.action.edit.*;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentGive;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentRemove;
import me.mrdaniel.npcs.commands.main.*;
import me.mrdaniel.npcs.data.NPCKeys;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.listeners.InteractListener;
import me.mrdaniel.npcs.listeners.WorldListener;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.PlaceholderManager;
import me.mrdaniel.npcs.utils.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(id = NPCs.MODID,
		name = NPCs.NAME,
		version = NPCs.VERSION,
		description = NPCs.DESCRIPTION,
		authors = {"Daniel12321"},
		url = "https://github.com/Daniel12321/NPCs",
		dependencies = { @Dependency(id = "placeholderapi", version = "[4.1,)", optional = true) })
public class NPCs {

	public static final String MODID = "npcs";
	public static final String NAME = "NPCs";
	public static final String VERSION = "4.0.0";
	public static final String DESCRIPTION = "A plugin that adds simple custom NPC's to your worlds.";

	private static NPCs instance;
	public static NPCs getInstance() {
		return instance;
	}

	private final Game game;
	private final PluginContainer container;
	private final Logger logger;
	private final Path configDir;
	private final Config config;

	private final NPCManager npcManager;
	private final ActionManager actionManager;
	private final MenuManager menuManager;
	private final PlaceholderManager placeholderManager;

	@Inject
	public NPCs(Game game, PluginContainer container, @ConfigDir(sharedRoot = false) Path path, MetricsLite metrics) {
		instance = this;

		this.game = game;
		this.container = container;
		this.logger = LoggerFactory.getLogger("NPCs");
		this.configDir = path;

		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
			if (!Files.exists(path.resolve("config.conf"))) {
				this.container.getAsset("config.conf").get().copyToFile(path);
			}
		} catch (final IOException exc) {
			this.logger.error("Failed to create config asset: ", exc);
		}

		this.config = new Config(path.resolve("config.conf"));
		this.npcManager = new NPCManager(this.config);
		this.actionManager = new ActionManager();
		this.menuManager = new MenuManager();
		this.placeholderManager = new PlaceholderManager();
	}

	@Listener
	public void onPreInit(@Nullable GamePreInitializationEvent e) {
		this.game.getRegistry().registerModule(NPCType.class, new NPCTypeRegistryModule());
		this.game.getRegistry().registerModule(GlowColor.class, new GlowColorRegistryModule());
		this.game.getRegistry().registerModule(Career.class, new CareerRegistryModule());
		this.game.getRegistry().registerModule(CatType.class, new CatTypeRegistryModule());
		this.game.getRegistry().registerModule(HorseColor.class, new HorseColorRegistryModule());
		this.game.getRegistry().registerModule(HorsePattern.class, new HorsePatternRegistryModule());
		this.game.getRegistry().registerModule(LlamaType.class, new LlamaTypeRegistryModule());
		this.game.getRegistry().registerModule(ParrotType.class, new ParrotTypeRegistryModule());
		this.game.getRegistry().registerModule(RabbitType.class, new RabbitTypeRegistryModule());

		this.game.getRegistry().registerModule(PageType.class, new PageTypeRegistryModule());
		this.game.getRegistry().registerModule(ActionType.class, new ActionTypeRegistryModule());
		this.game.getRegistry().registerModule(ConditionType.class, new ConditionTypeRegistryModule());
		this.game.getRegistry().registerModule(PropertyType.class, new PropertyTypeRegistryModule());

		this.npcManager.setup();

		NPCKeys.init();
		NPCDataBuilder.register();
	}

	@Listener
	public void onInit(@Nullable GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		this.npcManager.load();
		this.placeholderManager.load();

		this.game.getEventManager().registerListeners(this, new InteractListener());
		if (this.config.getNode("npc_respawn_on_world_load").getBoolean(true)) {
			this.game.getEventManager().registerListeners(this, new WorldListener());
		}

		this.game.getCommandManager().register(this, CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Main Command"))
				.executor(new CommandInfo())
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
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.NAME), "name")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.NAME_VISIBLE), "namevisible")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SKIN), "skin")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SKIN_UUID), "skinuuid")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.LOOKING), "looking")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.INTERACT), "interact")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.GLOWING), "glowing")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SILENT), "silent")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.GLOWCOLOR), "glowcolor")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.BABY), "baby")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.CHARGED), "charged")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.ANGRY), "angry")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SIZE), "size")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SITTING), "sitting")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.SADDLE), "saddle")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.HANGING), "hanging")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.PUMPKIN), "pumpkin")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.CAREER), "career")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.HORSEPATTERN), "horsepattern")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.HORSECOLOR), "horsecolor")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.LLAMATYPE), "llamatype")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.CATTYPE), "cattype")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.RABBITTYPE), "rabbittype")
				.child(CommandEdit.build(PageTypes.MAIN, PropertyTypes.PARROTTYPE), "parrottype")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Helmet"))
						.child(CommandSpec.builder().description(this.desc("Give Helmet")).permission("npc.armor.helmet.give").executor(new CommandEquipmentGive.Helmet()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove Helmet")).permission("npc.armor.helmet.remove").executor(new CommandEquipmentRemove.Helmet()).build(), "remove")
						.build(), "helmet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Chestplate"))
						.child(CommandSpec.builder().description(this.desc("Give Chestplate")).permission("npc.armor.chestplate.give").executor(new CommandEquipmentGive.Chestplate()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove Chestplate")).permission("npc.armor.chestplate.remove").executor(new CommandEquipmentRemove.Chestplate()).build(), "remove")
						.build(), "chestplate")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Leggings"))
						.child(CommandSpec.builder().description(this.desc("Give Leggings")).permission("npc.armor.leggings.give").executor(new CommandEquipmentGive.Leggings()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove Leggings")).permission("npc.armor.leggings.remove").executor(new CommandEquipmentRemove.Leggings()).build(), "remove")
						.build(), "leggings")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Boots"))
						.child(CommandSpec.builder().description(this.desc("Give Boots")).permission("npc.armor.boots.give").executor(new CommandEquipmentGive.Boots()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove Boots")).permission("npc.armor.boots.remove").executor(new CommandEquipmentRemove.Boots()).build(), "remove")
						.build(), "boots")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | MainHand"))
						.child(CommandSpec.builder().description(this.desc("Give MainHand")).permission("npc.armor.mainhand.give").executor(new CommandEquipmentGive.MainHand()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove MainHand")).permission("npc.armor.mainhand.remove").executor(new CommandEquipmentRemove.MainHand()).build(), "remove")
						.build(), "mainhand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | OffHand"))
						.child(CommandSpec.builder().description(this.desc("Give OffHand")).permission("npc.armor.offhand.give").executor(new CommandEquipmentGive.OffHand()).build(), "give")
						.child(CommandSpec.builder().description(this.desc("Remove OffHand")).permission("npc.armor.offhand.remove").executor(new CommandEquipmentRemove.OffHand()).build(), "remove")
						.build(), "offhand")
				.child(CommandSpec.builder().description(this.desc("Actions"))
						.child(CommandSpec.builder().description(this.desc("Set Repeat Actions")).permission("npc.action.repeat").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("repeat")))).executor(new CommandActionRepeat()).build(), "repeat")
						.child(CommandSpec.builder().description(this.desc("Swap Actions")).permission("npc.action.swap").arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second"))).executor(new CommandActionSwap()).build(), "swap")
						.child(CommandSpec.builder().description(this.desc("Remove Action")).permission("npc.action.remove").arguments(GenericArguments.integer(Text.of("number"))).executor(new CommandActionRemove()).build(), "remove")
						.child(CommandSpec.builder().description(this.desc("Add Action"))
								.child(CommandSpec.builder().description(this.desc("Add Player Command Action")).permission("npc.action.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.PlayerCommand()).build(), "playercmd")
								.child(CommandSpec.builder().description(this.desc("Add Console Command Action")).permission("npc.action.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.ConsoleCommand()).build(), "consolecmd")
								.child(CommandSpec.builder().description(this.desc("Add Message Action")).permission("npc.action.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandActionAdd.Message()).build(), "message")
								.child(CommandSpec.builder().description(this.desc("Add Delay Action")).permission("npc.action.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandActionAdd.Delay()).build(), "delay")
								.child(CommandSpec.builder().description(this.desc("Add Cooldown Action")).permission("npc.action.cooldown").arguments(GenericArguments.integer(Text.of("seconds")), GenericArguments.string(Text.of("message"))).executor(new CommandActionAdd.Cooldown()).build(), "cooldown")
								.child(CommandSpec.builder().description(this.desc("Add Pause Action")).permission("npc.action.pause").executor(new CommandActionAdd.Pause()).build(), "pause")
								.child(CommandSpec.builder().description(this.desc("Add Goto Action")).permission("npc.action.goto").arguments(GenericArguments.integer(Text.of("next"))).executor(new CommandActionAdd.Goto()).build(), "goto")
								.child(CommandSpec.builder().description(this.desc("Add Choices Action")).permission("npc.action.choices").arguments(GenericArguments.string(Text.of("first")), GenericArguments.integer(Text.of("goto_first")), GenericArguments.string(Text.of("second")), GenericArguments.integer(Text.of("goto_second"))).executor(new CommandActionAdd.Choices()).build(), "choices")
								.child(CommandSpec.builder().description(this.desc("Add Condition Action"))
										.child(CommandSpec.builder().description(this.desc("Add Item Condition Action")).permission("npc.action.condition.item").arguments(GenericArguments.catalogedElement(Text.of("type"), ItemType.class), GenericArguments.integer(Text.of("amount")), GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandActionAddCondition.Item()).build(), "item")
										.child(CommandSpec.builder().description(this.desc("Add Money Condition Action")).permission("npc.action.condition.money").arguments(GenericArguments.catalogedElement(Text.of("type"), Currency.class), GenericArguments.doubleNum(Text.of("amount"))).executor(new CommandActionAddCondition.Money()).build(), "money")
										.child(CommandSpec.builder().description(this.desc("Add Level Condition Action")).permission("npc.action.condition.level").arguments(GenericArguments.integer(Text.of("level"))).executor(new CommandActionAddCondition.Level()).build(), "level")
										.child(CommandSpec.builder().description(this.desc("Add Exp Condition Action")).permission("npc.action.condition.exp").arguments(GenericArguments.integer(Text.of("exp"))).executor(new CommandActionAddCondition.Exp()).build(), "exp")
										.build(), "condition")
								.build(), "add")
						.child(CommandSpec.builder().description(this.desc("Edit Action")).arguments(GenericArguments.integer(Text.of("index")))
								.child(CommandSpec.builder().description(this.desc("Set Console Command")).permission("npc.action.edit.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetConsoleCommand()).build(), "consolecmd")
								.child(CommandSpec.builder().description(this.desc("Set Player Command")).permission("npc.action.edit.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetPlayerCommand()).build(), "playercmd")
								.child(CommandSpec.builder().description(this.desc("Set Message")).permission("npc.action.edit.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandSetMessage()).build(), "message")
								.child(CommandSpec.builder().description(this.desc("Set Goto")).permission("npc.action.edit.goto").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGoto()).build(), "goto")
								.child(CommandSpec.builder().description(this.desc("Set Delay")).permission("npc.action.edit.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandSetDelay()).build(), "delay")
								.child(CommandSpec.builder().description(this.desc("Set Cooldown Time")).permission("npc.action.edit.cooldown.time").arguments(GenericArguments.integer(Text.of("seconds"))).executor(new CommandSetCooldownTime()).build(), "cooldowntime")
								.child(CommandSpec.builder().description(this.desc("Set Cooldown Message")).permission("npc.action.edit.cooldown.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandSetCooldownMessage()).build(), "cooldownmessage")
								.child(CommandSpec.builder().description(this.desc("Take")).permission("npc.action.edit.condition.take").arguments(GenericArguments.bool(Text.of("take"))).executor(new CommandSetTake()).build(), "take")
								.child(CommandSpec.builder().description(this.desc("Goto Failed")).permission("npc.action.edit.condition.goto.failed").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoFailed()).build(), "goto_failed")
								.child(CommandSpec.builder().description(this.desc("Goto Met")).permission("npc.action.edit.condition.goto.met").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoMet()).build(), "goto_met")
								.child(CommandSpec.builder().description(this.desc("Add Choice")).permission("npc.action.edit.choice.add").arguments(GenericArguments.string(Text.of("name")), GenericArguments.integer(Text.of("goto"))).executor(new CommandAddChoice()).build(), "addchoice", "setchoice")
								.child(CommandSpec.builder().description(this.desc("Remove Choice")).permission("npc.action.edit.choice.remove").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandRemoveChoice()).build(), "removechoice")
								.build(), "edit")
						.build(), "action", "actions")
				.build(), "npc", "npcs");

		this.logger.info("Loaded plugin successfully.");

		if (this.config.getNode("update_message").getBoolean(true)) {
			Task.builder().async().execute(() -> {
				ServerUtils.getLatestVersion().ifPresent(v -> {
					Task.builder().execute(() -> {
						if (!v.equals("v" + NPCs.VERSION)) {
							this.logger.info("A new version (" + v + ") of NPCs is available!");
							this.logger.info("It can be downloaded from https://github.com/Daniel12321/NPCs/releases");
						}
					}).submit(this);
				});
			}).submit(this);
		}
	}

	private Text desc(String str) {
		return Text.of(TextColors.GOLD, "NPCs | ", str);
	}

	@Listener
	public void onStopping(@Nullable GameStoppingServerEvent e) {
		this.logger.info("Unloading Plugin...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks().forEach(Task::cancel);
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.logger.info("Unloaded plugin successfully.");
	}

	@Listener
	public void onReload(@Nullable GameReloadEvent e) {
		this.onStopping(null);
		this.onInit(null);

		this.game.getServer().getWorlds().forEach(world -> {
			this.npcManager.getNPCs(world.getName()).forEach(data -> {
				try {
					this.npcManager.spawn(data);
				} catch (NPCException exc) {
					this.logger.error("Failed to respawn NPC " + data.getId() + ": ", exc);
				}
			});
		});
	}

	@Listener
	public void onServiceChange(ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			ConditionMoney.setEconomyService((EconomyService) e.getNewProvider());
		}
	}

	@Listener(order = Order.LATE)
	public void onQuit(ClientConnectionEvent.Disconnect e) {
		this.menuManager.deselect(e.getTargetEntity().getUniqueId());
		this.actionManager.removeChoosing(e.getTargetEntity().getUniqueId());
	}

	public Game getGame() {
		return this.game;
	}

	public PluginContainer getContainer() {
		return this.container;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public Path getConfigDir() {
		return this.configDir;
	}

	public Config getConfig() {
		return this.config;
	}

	public NPCManager getNPCManager() {
		return npcManager;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}
}
