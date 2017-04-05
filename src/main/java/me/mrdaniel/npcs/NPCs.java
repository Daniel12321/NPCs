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
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import me.mrdaniel.npcs.bstats.MetricsLite;
import me.mrdaniel.npcs.commands.ChoiceMaps;
import me.mrdaniel.npcs.commands.CommandCopy;
import me.mrdaniel.npcs.commands.CommandCreate;
import me.mrdaniel.npcs.commands.CommandDeselect;
import me.mrdaniel.npcs.commands.CommandInfo;
import me.mrdaniel.npcs.commands.CommandMount;
import me.mrdaniel.npcs.commands.CommandRemove;
import me.mrdaniel.npcs.commands.action.CommandActionMode;
import me.mrdaniel.npcs.commands.action.CommandActionRemove;
import me.mrdaniel.npcs.commands.action.CommandActionRepeating;
import me.mrdaniel.npcs.commands.action.CommandActionSwap;
import me.mrdaniel.npcs.commands.action.CommandAddConsoleCommandAction;
import me.mrdaniel.npcs.commands.action.CommandAddMessageAction;
import me.mrdaniel.npcs.commands.action.CommandAddPlayerCommandAction;
import me.mrdaniel.npcs.commands.armor.CommandArmor;
import me.mrdaniel.npcs.commands.armor.CommandHand;
import me.mrdaniel.npcs.commands.armor.CommandTakeArmor;
import me.mrdaniel.npcs.commands.armor.CommandTakeHand;
import me.mrdaniel.npcs.commands.edit.CommandCareer;
import me.mrdaniel.npcs.commands.edit.CommandCat;
import me.mrdaniel.npcs.commands.edit.CommandCharge;
import me.mrdaniel.npcs.commands.edit.CommandColor;
import me.mrdaniel.npcs.commands.edit.CommandGlow;
import me.mrdaniel.npcs.commands.edit.CommandInteract;
import me.mrdaniel.npcs.commands.edit.CommandLlama;
import me.mrdaniel.npcs.commands.edit.CommandLook;
import me.mrdaniel.npcs.commands.edit.CommandMove;
import me.mrdaniel.npcs.commands.edit.CommandName;
import me.mrdaniel.npcs.commands.edit.CommandSit;
import me.mrdaniel.npcs.commands.edit.CommandSize;
import me.mrdaniel.npcs.commands.edit.CommandSkin;
import me.mrdaniel.npcs.commands.edit.CommandStyle;
import me.mrdaniel.npcs.data.action.Action;
import me.mrdaniel.npcs.data.action.ActionBuilder;
import me.mrdaniel.npcs.data.action.NPCActions;
import me.mrdaniel.npcs.data.action.NPCActionsBuilder;
import me.mrdaniel.npcs.data.npc.ImmutableNPCData;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.listeners.WorldListener;
import me.mrdaniel.npcs.manager.NPCManager;
import me.mrdaniel.npcs.manager.PlaceHolderManager;
import me.mrdaniel.npcs.manager.placeholder.PlaceHolderAPIManager;
import me.mrdaniel.npcs.manager.placeholder.SimplePlaceHolderManager;

@Plugin(id = "npcs",
		name = "NPCs",
		version = "1.1.1-API6",
		description = "A plugin that adds simple custom NPC's to your worlds.",
		authors = {"Daniel12321"},
		dependencies = { @Dependency(id = "placeholderapi", optional = true) })
public class NPCs {

	private final Game game;
	private final PluginContainer container;
	private final Logger logger;
	private final Path configDir;

	private NPCManager npcmanager;
	private PlaceHolderManager placeholders;

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

	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
		this.game.getDataManager().register(NPCData.class, ImmutableNPCData.class, new NPCDataBuilder());
		this.game.getDataManager().registerBuilder(Action.class, new ActionBuilder());
		this.game.getDataManager().registerBuilder(NPCActions.class, new NPCActionsBuilder());
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		Config config = new Config(this, this.configDir.resolve("config.conf"));

		ChoiceMaps choices = new ChoiceMaps(this);

		this.npcmanager = new NPCManager(this, config);

		try {
			this.placeholders = new PlaceHolderAPIManager(this, config);
			this.logger.info("Found PlaceholderAPI: Loaded successfully.");
		}
		catch (final Throwable exc) {
			this.logger.info("Could not find PlaceholderAPI: Loading a simple version instead.");
			this.logger.debug(exc.getClass().getSimpleName(), ": ", exc.getMessage());
			this.placeholders = new SimplePlaceHolderManager(config);
		}

		CommandSpec main = CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Main Command"))
				.executor(new CommandInfo(this))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Create Command")).permission("npc.create").arguments(GenericArguments.optional(GenericArguments.choices(Text.of("type"), choices.getEntities()))).executor(new CommandCreate(this)).build(), "create")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Command")).permission("npc.remove").executor(new CommandRemove(this)).build(), "remove")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Copy Command")).permission("npc.copy").executor(new CommandCopy(this)).build(), "copy")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Mount Command")).permission("npc.mount").executor(new CommandMount(this)).build(), "mount")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Move Command")).permission("npc.edit.move").executor(new CommandMove(this)).build(), "move")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Deselect Command")).permission("npc.edit.deselect").executor(new CommandDeselect(this)).build(), "deselect")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Name Command")).permission("npc.edit.name").arguments(GenericArguments.remainingJoinedStrings(Text.of("name"))).executor(new CommandName(this)).build(), "name")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Look Command")).permission("npc.edit.look").arguments(GenericArguments.bool(Text.of("value"))).executor(new CommandLook(this)).build(), "look")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Interact Command")).permission("npc.edit.interact").arguments(GenericArguments.bool(Text.of("value"))).executor(new CommandInteract(this)).build(), "interact")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Glow Command")).permission("npc.edit.glow").arguments(GenericArguments.bool(Text.of("value"))).executor(new CommandGlow(this)).build(), "glow")

				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Skin Command")).permission("npc.edit.skin").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSkin(this)).build(), "skin")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Career Command")).permission("npc.edit.career").arguments(GenericArguments.choices(Text.of("type"), choices.getCareers())).executor(new CommandCareer(this)).build(), "career")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Cat Command")).permission("npc.edit.cat").arguments(GenericArguments.choices(Text.of("type"), choices.getCats())).executor(new CommandCat(this)).build(), "cat")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Llama Command")).permission("npc.edit.llama").arguments(GenericArguments.choices(Text.of("type"), choices.getLlamas())).executor(new CommandLlama(this)).build(), "llama")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Style Command")).permission("npc.edit.style").arguments(GenericArguments.choices(Text.of("style"), choices.getStyles())).executor(new CommandStyle(this)).build(), "style")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Color Command")).permission("npc.edit.color").arguments(GenericArguments.choices(Text.of("color"), choices.getColors())).executor(new CommandColor(this)).build(), "color")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Size Command")).permission("npc.edit.size").arguments(GenericArguments.integer(Text.of("size"))).executor(new CommandSize(this)).build(), "size")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Sit Command")).permission("npc.edit.sit").arguments(GenericArguments.bool(Text.of("value"))).executor(new CommandSit(this)).build(), "sit")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Charge Command")).permission("npc.edit.charge").arguments(GenericArguments.bool(Text.of("value"))).executor(new CommandCharge(this)).build(), "charge")

				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Helmet Command")).permission("npc.armor.helmet.give").executor(new CommandArmor(this, EquipmentTypes.HEADWEAR)).build(), "helmet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take Helmet Command")).permission("npc.armor.helmet.take").executor(new CommandTakeArmor(this, EquipmentTypes.HEADWEAR)).build(), "takehelmet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Chestplate Command")).permission("npc.armor.chestplate.give").executor(new CommandArmor(this, EquipmentTypes.CHESTPLATE)).build(), "chestplate")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take Chestplate Command")).permission("npc.armor.chestplate.take").executor(new CommandTakeArmor(this, EquipmentTypes.CHESTPLATE)).build(), "takechestplate")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Leggings Command")).permission("npc.armor.leggings.give").executor(new CommandArmor(this, EquipmentTypes.LEGGINGS)).build(), "leggings")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take Leggings Command")).permission("npc.armor.leggings.take").executor(new CommandTakeArmor(this, EquipmentTypes.LEGGINGS)).build(), "takeleggings")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Boots Command")).permission("npc.armor.boots.give").executor(new CommandArmor(this, EquipmentTypes.BOOTS)).build(), "boots")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take Boots Command")).permission("npc.armor.boots.take").executor(new CommandTakeArmor(this, EquipmentTypes.BOOTS)).build(), "takeboots")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give Hand Command")).permission("npc.armor.hand.give").executor(new CommandHand(this, HandTypes.MAIN_HAND)).build(), "hand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take Hand Command")).permission("npc.armor.hand.take").executor(new CommandTakeHand(this, HandTypes.MAIN_HAND)).build(), "takehand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Give OffHand Command")).permission("npc.armor.offhand.give").executor(new CommandHand(this, HandTypes.OFF_HAND)).build(), "offhand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Take OffHand Command")).permission("npc.armor.offhand.take").executor(new CommandTakeHand(this, HandTypes.OFF_HAND)).build(), "takeoffhand")

				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Player Command Action Command")).permission("npc.action.command.player").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandAddPlayerCommandAction(this)).build(), "addplayercmd")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Console Command Action Command")).permission("npc.action.command.console").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandAddConsoleCommandAction(this)).build(), "addconsolecmd")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Add Message Action Command")).permission("npc.action.message").arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandAddMessageAction(this)).build(), "addmessage")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Action Command")).permission("npc.action.remove").arguments(GenericArguments.integer(Text.of("number"))).executor(new CommandActionRemove(this)).build(), "removeaction")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Swap Actions Command")).permission("npc.action.swap").arguments(GenericArguments.integer(Text.of("first")), GenericArguments.integer(Text.of("second"))).executor(new CommandActionSwap(this)).build(), "swap")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Action Mode Command")).permission("npc.action.mode").arguments(GenericArguments.string(Text.of("mode"))).executor(new CommandActionMode(this)).build(), "mode")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Repeating Action Option Command")).permission("npc.action.mode").arguments(GenericArguments.bool(Text.of("repeating"))).executor(new CommandActionRepeating(this)).build(), "repeating")
				.build();

		this.game.getCommandManager().register(this, main, "npc", "npcs");

		this.game.getEventManager().registerListeners(this, new WorldListener(this));

		this.logger.info("Plugin loaded successfully.");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks(this).forEach(task -> task.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.onInit(null);
	}

	@Nonnull public Game getGame() { return this.game; }
	@Nonnull public PluginContainer getContainer() { return this.container; }
	@Nonnull public Path getConfigDir() { return this.configDir; }
	@Nonnull public Logger getLogger() { return this.logger; }

	@Nonnull public NPCManager getNPCManager() { return this.npcmanager; }
	@Nonnull public PlaceHolderManager getPlaceHolderManager() { return this.placeholders; }
}