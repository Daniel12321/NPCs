package me.mrdaniel.npcs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.type.Career;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.OcelotType;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;

import me.mrdaniel.npcs.bstats.MetricsLite;
import me.mrdaniel.npcs.catalogtypes.actions.ActionType;
import me.mrdaniel.npcs.catalogtypes.actions.ActionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.conditions.ConditionType;
import me.mrdaniel.npcs.catalogtypes.conditions.ConditionTypeRegistryModule;
import me.mrdaniel.npcs.commands.action.CommandActionAdd;
import me.mrdaniel.npcs.commands.action.CommandActionRemove;
import me.mrdaniel.npcs.commands.action.CommandActionRepeat;
import me.mrdaniel.npcs.commands.action.CommandActionSwap;
import me.mrdaniel.npcs.commands.action.condition.CommandActionAddCondition;
import me.mrdaniel.npcs.commands.action.edit.CommandAddChoice;
import me.mrdaniel.npcs.commands.action.edit.CommandRemoveChoice;
import me.mrdaniel.npcs.commands.action.edit.CommandSetConsoleCommand;
import me.mrdaniel.npcs.commands.action.edit.CommandSetDelay;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGoto;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGotoFailed;
import me.mrdaniel.npcs.commands.action.edit.CommandSetGotoMet;
import me.mrdaniel.npcs.commands.action.edit.CommandSetMessage;
import me.mrdaniel.npcs.commands.action.edit.CommandSetPlayerCommand;
import me.mrdaniel.npcs.commands.action.edit.CommandSetTake;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentGive;
import me.mrdaniel.npcs.commands.armor.CommandEquipmentRemove;
import me.mrdaniel.npcs.commands.edit.CommandAngry;
import me.mrdaniel.npcs.commands.edit.CommandCareer;
import me.mrdaniel.npcs.commands.edit.CommandCat;
import me.mrdaniel.npcs.commands.edit.CommandCharged;
import me.mrdaniel.npcs.commands.edit.CommandColor;
import me.mrdaniel.npcs.commands.edit.CommandGlow;
import me.mrdaniel.npcs.commands.edit.CommandGlowColor;
import me.mrdaniel.npcs.commands.edit.CommandInteract;
import me.mrdaniel.npcs.commands.edit.CommandLlama;
import me.mrdaniel.npcs.commands.edit.CommandLook;
import me.mrdaniel.npcs.commands.edit.CommandMove;
import me.mrdaniel.npcs.commands.edit.CommandName;
import me.mrdaniel.npcs.commands.edit.CommandSit;
import me.mrdaniel.npcs.commands.edit.CommandSize;
import me.mrdaniel.npcs.commands.edit.CommandSkin;
import me.mrdaniel.npcs.commands.edit.CommandStyle;
import me.mrdaniel.npcs.commands.main.CommandCopy;
import me.mrdaniel.npcs.commands.main.CommandCreate;
import me.mrdaniel.npcs.commands.main.CommandDeselect;
import me.mrdaniel.npcs.commands.main.CommandInfo;
import me.mrdaniel.npcs.commands.main.CommandList;
import me.mrdaniel.npcs.commands.main.CommandMount;
import me.mrdaniel.npcs.commands.main.CommandRemove;
import me.mrdaniel.npcs.data.npc.ImmutableNPCData;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.data.npc.actions.Action;
import me.mrdaniel.npcs.data.npc.actions.ActionTypeSerializer;
import me.mrdaniel.npcs.data.npc.actions.conditions.Condition;
import me.mrdaniel.npcs.data.npc.actions.conditions.ConditionTypeSerializer;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.listeners.WorldListener;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.GlowColorManager;
import me.mrdaniel.npcs.managers.MenuManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.PlaceHolderManager;
import me.mrdaniel.npcs.managers.placeholders.PlaceHolderAPIManager;
import me.mrdaniel.npcs.managers.placeholders.SimplePlaceHolderManager;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;

@Plugin(id = "npcs",
		name = "NPCs",
		version = "2.0.0-API6",
		authors = {"Daniel12321"},
		url = "https://github.com/Daniel12321/NPCs",
		description = "A plugin that adds simple custom NPC's to your worlds.",
		dependencies = { @Dependency(id = "placeholderapi", optional = true) })
public class NPCs {

	private final Game game;
	private final PluginContainer container;
	private final Logger logger;
	private final Path configDir;

	private NPCManager npcmanager;
	private ActionManager actions;
	private MenuManager menus;
	private GlowColorManager glowcolors;
	private PlaceHolderManager placeholders;

	private int startup;

	@Inject
	public NPCs(final Game game, final PluginContainer container, @ConfigDir(sharedRoot = false) final Path path, final MetricsLite metrics) {
		this.game = game;
		this.container = container;
		this.logger = LoggerFactory.getLogger("NPCs");
		this.configDir = path;

		if (!Files.exists(path)) {
			try { Files.createDirectory(path); }
			catch (final IOException exc) { this.logger.error("Failed to create main config directory: {}", exc); }
		}
	}

	@SuppressWarnings("deprecation")
	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
//		DataRegistration.builder().dataClass(NPCData.class).immutableClass(ImmutableNPCData.class).builder(new NPCDataBuilder()).dataName("npc").manipulatorId("npc").buildAndRegister(this.container);

		this.game.getDataManager().register(NPCData.class, ImmutableNPCData.class, new NPCDataBuilder());
		this.game.getRegistry().registerModule(ActionType.class, new ActionTypeRegistryModule());
		this.game.getRegistry().registerModule(ConditionType.class, new ConditionTypeRegistryModule());

		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		Config config = new Config(this, this.configDir.resolve("config.conf"));

		this.npcmanager = new NPCManager(this, this.configDir.resolve("storage"));
		this.actions = new ActionManager(this);
		this.menus = new MenuManager(this, config);
		this.glowcolors = new GlowColorManager(this);
		this.startup = new Random().nextInt(Integer.MAX_VALUE);

		try {
			this.placeholders = new PlaceHolderAPIManager(this, config);
			this.logger.info("Found PlaceholderAPI: Loaded successfully.");
		}
		catch (final Throwable exc) {
			this.placeholders = new SimplePlaceHolderManager(config);
			this.logger.info("Could not find PlaceholderAPI: Loading a simple version instead.");
		}

		this.game.getCommandManager().register(this, CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Main Command"))
			.executor(new CommandInfo(this))
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | List Command")).permission("npc.list").executor(new CommandList(this)).build(), "list")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Create Command")).permission("npc.create").arguments(GenericArguments.optional(GenericArguments.catalogedElement(Text.of("type"), EntityType.class))).executor(new CommandCreate(this)).build(), "create")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Command")).permission("npc.remove").executor(new CommandRemove(this)).build(), "remove")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Copy Command")).permission("npc.copy").executor(new CommandCopy(this)).build(), "copy")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Mount Command")).permission("npc.mount").executor(new CommandMount(this)).build(), "mount")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Deselect Command")).permission("npc.edit.deselect").executor(new CommandDeselect(this)).build(), "deselect")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Move Command")).permission("npc.edit.move").executor(new CommandMove(this)).build(), "move")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Name Command")).permission("npc.edit.name").arguments(GenericArguments.remainingJoinedStrings(Text.of("name"))).executor(new CommandName(this)).build(), "name")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Look Command")).permission("npc.edit.look").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("look")))).executor(new CommandLook(this)).build(), "look")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Interact Command")).permission("npc.edit.interact").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("interact")))).executor(new CommandInteract(this)).build(), "interact")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Glow Command")).permission("npc.edit.glow").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("glow")))).executor(new CommandGlow(this)).build(), "glow")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | GlowColor Command")).permission("npc.edit.glowcolor").arguments(GenericArguments.catalogedElement(Text.of("color"), TextColor.class)).executor(new CommandGlowColor(this)).build(), "glowcolor")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Skin Command")).permission("npc.edit.skin").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSkin(this)).build(), "skin")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Career Command")).permission("npc.edit.career").arguments(GenericArguments.catalogedElement(Text.of("career"), Career.class)).executor(new CommandCareer(this)).build(), "career")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Cat Command")).permission("npc.edit.cat").arguments(GenericArguments.catalogedElement(Text.of("cat"), OcelotType.class)).executor(new CommandCat(this)).build(), "cat")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Llama Command")).permission("npc.edit.llama").arguments(GenericArguments.catalogedElement(Text.of("variant"), LlamaVariant.class)).executor(new CommandLlama(this)).build(), "llama")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Style Command")).permission("npc.edit.style").arguments(GenericArguments.catalogedElement(Text.of("style"), HorseStyle.class)).executor(new CommandStyle(this)).build(), "style")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Color Command")).permission("npc.edit.color").arguments(GenericArguments.catalogedElement(Text.of("color"), HorseColor.class)).executor(new CommandColor(this)).build(), "color")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Size Command")).permission("npc.edit.size").arguments(GenericArguments.integer(Text.of("size"))).executor(new CommandSize(this)).build(), "size")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Sit Command")).permission("npc.edit.sit").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("sit")))).executor(new CommandSit(this)).build(), "sit")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Charged Command")).permission("npc.edit.charge").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("charged")))).executor(new CommandCharged(this)).build(), "charged")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Anrgy Command")).permission("npc.edit.angry").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("angry")))).executor(new CommandAngry(this)).build(), "angry")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Helmet Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Helmet Command")).permission("npc.armor.helmet.give").executor(new CommandEquipmentGive.Helmet(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Helmet Command")).permission("npc.armor.helmet.remove").executor(new CommandEquipmentRemove.Helmet(this)).build(), "remove")
				.build(), "helmet")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Chestplate Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Chestplate Command")).permission("npc.armor.chestplate.give").executor(new CommandEquipmentGive.Chestplate(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Chestplate Command")).permission("npc.armor.chestplate.remove").executor(new CommandEquipmentRemove.Chestplate(this)).build(), "remove")
				.build(), "chestplate")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Leggings Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Leggings Command")).permission("npc.armor.leggings.give").executor(new CommandEquipmentGive.Leggings(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Leggings Command")).permission("npc.armor.leggings.remove").executor(new CommandEquipmentRemove.Leggings(this)).build(), "remove")						
				.build(), "leggings")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | Boots Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Boots Command")).permission("npc.armor.boots.give").executor(new CommandEquipmentGive.Boots(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Boots Command")).permission("npc.armor.boots.remove").executor(new CommandEquipmentRemove.Boots(this)).build(), "remove")
				.build(), "boots")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | MainHand Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give MainHand Command")).permission("npc.armor.mainhand.give").executor(new CommandEquipmentGive.MainHand(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove MainHand Command")).permission("npc.armor.mainhand.remove").executor(new CommandEquipmentRemove.MainHand(this)).build(), "remove")
				.build(), "mainhand")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPC | OffHand Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give OffHand Command")).permission("npc.armor.offhand.give").executor(new CommandEquipmentGive.OffHand(this)).build(), "give")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove OffHand Command")).permission("npc.armor.offhand.remove").executor(new CommandEquipmentRemove.OffHand(this)).build(), "remove")
				.build(), "offhand")
			.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Actions Command"))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Repeat Actions Command")).permission("npc.action.repeat").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("repeat")))).executor(new CommandActionRepeat(this)).build(), "repeat")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Swap Actions Command")).permission("npc.action.swap").arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second"))).executor(new CommandActionSwap(this)).build(), "swap")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Action Command")).permission("npc.action.remove").arguments(GenericArguments.integer(Text.of("number"))).executor(new CommandActionRemove(this)).build(), "remove")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Action Command"))
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Player Command Action Command")).permission("npc.action.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.PlayerCommand(this)).build(), "playercmd")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Console Command Action Command")).permission("npc.action.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandActionAdd.ConsoleCommand(this)).build(), "consolecmd")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Message Action Command")).permission("npc.action.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandActionAdd.Message(this)).build(), "message")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Delay Action Command")).permission("npc.action.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandActionAdd.Delay(this)).build(), "delay")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Pause Action Command")).permission("npc.action.pause").executor(new CommandActionAdd.Pause(this)).build(), "pause")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Goto Action Command")).permission("npc.action.goto").arguments(GenericArguments.integer(Text.of("next"))).executor(new CommandActionAdd.Goto(this)).build(), "goto")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Choices Action Command")).permission("npc.action.choices").arguments(GenericArguments.string(Text.of("first")), GenericArguments.integer(Text.of("goto_first")), GenericArguments.string(Text.of("second")), GenericArguments.integer(Text.of("goto_second"))).executor(new CommandActionAdd.Choices(this)).build(), "choices")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Condition Action Command")).arguments(GenericArguments.integer(Text.of("goto_failed")), GenericArguments.integer(Text.of("goto_met")), GenericArguments.bool(Text.of("take")))
						.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Item Condition Action Command")).permission("npc.action.condition.item").arguments(GenericArguments.catalogedElement(Text.of("type"), ItemType.class), GenericArguments.integer(Text.of("amount")), GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandActionAddCondition.Item(this)).build(), "item")
						.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Level Condition Action Command")).permission("npc.action.condition.level").arguments(GenericArguments.integer(Text.of("level"))).executor(new CommandActionAddCondition.Level(this)).build(), "level")
						.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Exp Condition Action Command")).permission("npc.action.condition.exp").arguments(GenericArguments.integer(Text.of("exp"))).executor(new CommandActionAddCondition.Exp(this)).build(), "exp")
						.build(), "condition")
					.build(), "add")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Edit Action Command")).arguments(GenericArguments.integer(Text.of("index")))
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Console Command Command")).permission("npc.action.edit.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetConsoleCommand(this)).build(), "consolecmd")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Player Command Command")).permission("npc.action.edit.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSetPlayerCommand(this)).build(), "playercmd")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Message Command")).permission("npc.action.edit.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandSetMessage(this)).build(), "message")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Goto Command")).permission("npc.action.edit.goto").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGoto(this)).build(), "goto")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Delay Command")).permission("npc.action.edit.delay").arguments(GenericArguments.integer(Text.of("ticks"))).executor(new CommandSetDelay(this)).build(), "delay")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Take Command")).permission("npc.action.edit.condition.take").arguments(GenericArguments.bool(Text.of("take"))).executor(new CommandSetTake(this)).build(), "take")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Goto Failed Command")).permission("npc.action.edit.condition.goto.failed").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoFailed(this)).build(), "goto_failed")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Set Goto Met Command")).permission("npc.action.edit.condition.goto.met").arguments(GenericArguments.integer(Text.of("goto"))).executor(new CommandSetGotoMet(this)).build(), "goto_met")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Choice Command")).permission("npc.action.edit.choice.add").arguments(GenericArguments.string(Text.of("name")), GenericArguments.integer(Text.of("goto"))).executor(new CommandAddChoice(this)).build(), "addchoice", "setchoice")
					.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Choice Command")).permission("npc.action.edit.choice.remove").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandRemoveChoice(this)).build(), "removechoice")
					.build(), "edit")
				.build(), "action", "actions")
			.build(), "npc", "npcs");

		this.game.getEventManager().registerListeners(this, new WorldListener(this));

		Task.builder().delayTicks(50).intervalTicks(config.getNode("npc_update_ticks").getInt(2)).execute(() -> this.game.getServer().getWorlds().forEach(w -> w.getEntities().stream().filter(ent -> ent.get(NPCData.class).isPresent()).forEach(ent -> ent.get(NPCData.class).get().tick((Living)ent)))).submit(this);

		this.logger.info("Plugin loaded successfully.");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.logger.info("Reloading Plugin...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks(this).forEach(task -> task.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.onInit(null);

		this.game.getServer().getWorlds().forEach(w -> w.getEntities(ent -> ent.get(NPCData.class).isPresent()).forEach(ent -> ent.remove()));
		Task.builder().delayTicks(100).execute(() -> this.game.getServer().getWorlds().forEach(w -> this.npcmanager.load(w))).submit(this);
	}

	@Nonnull public Game getGame() { return this.game; }
	@Nonnull public PluginContainer getContainer() { return this.container; }
	@Nonnull public Path getConfigDir() { return this.configDir; }
	@Nonnull public Logger getLogger() { return this.logger; }

	@Nonnull public NPCManager getNPCManager() { return this.npcmanager; }
	@Nonnull public ActionManager getActionManager() { return this.actions; }
	@Nonnull public MenuManager getMenuManager() { return this.menus; }
	@Nonnull public GlowColorManager getGlowColorManager() { return this.glowcolors; }
	@Nonnull public PlaceHolderManager getPlaceHolderManager() { return this.placeholders; }
	public int getStartup() { return this.startup; }
}