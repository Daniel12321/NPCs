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
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.pagination.PaginationService;
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
import me.mrdaniel.npcs.commands.edit.CommandArmor;
import me.mrdaniel.npcs.commands.edit.CommandCareer;
import me.mrdaniel.npcs.commands.edit.CommandCat;
import me.mrdaniel.npcs.commands.edit.CommandCharged;
import me.mrdaniel.npcs.commands.edit.CommandGlow;
import me.mrdaniel.npcs.commands.edit.CommandHand;
import me.mrdaniel.npcs.commands.edit.CommandInteract;
import me.mrdaniel.npcs.commands.edit.CommandLlama;
import me.mrdaniel.npcs.commands.edit.CommandLook;
import me.mrdaniel.npcs.commands.edit.CommandMove;
import me.mrdaniel.npcs.commands.edit.CommandName;
import me.mrdaniel.npcs.commands.edit.CommandSit;
import me.mrdaniel.npcs.commands.edit.CommandSize;
import me.mrdaniel.npcs.commands.edit.CommandSkin;
import me.mrdaniel.npcs.data.npc.ImmutableNPCData;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.listeners.WorldListener;
import me.mrdaniel.npcs.manager.NPCManager;

@Plugin(id = "npcs",
		name = "NPCs",
		version = "1.0.0",
		description = "A plugin that adds simple NPC's to your worlds.",
		authors = {"Daniel12321"})
public class NPCs {

	private final Game game;
	private final PluginContainer container;
	private final Logger logger;
	private final Path configDir;

	private NPCManager npcmanager;

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
//		this.game.getDataManager().register(WalkingData.class, ImmutableWalkingData.class, new WalkingDataBuilder());
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		Config config = new Config(this, this.configDir.resolve("config.conf"));
		ChoiceMaps choices = new ChoiceMaps(this);

		this.npcmanager = new NPCManager(this, config);

		CommandSpec main = CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Main Command"))
				.executor(new CommandInfo(this.game.getServiceManager().provide(PaginationService.class).get()))
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Create Command")).permission("npc.create").arguments(GenericArguments.optional(GenericArguments.choices(Text.of("type"), choices.getEntities()))).executor(new CommandCreate(this)).build(), "create")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Remove Command")).permission("npc.remove").executor(new CommandRemove(this)).build(), "remove")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Copy Command")).permission("npc.copy").executor(new CommandCopy(this)).build(), "copy")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Mount Command")).permission("npc.mount").executor(new CommandMount(this)).build(), "mount")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Move Command")).permission("npc.edit.move").executor(new CommandMove(this)).build(), "move")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Deselect Command")).permission("npc.edit.deselect").executor(new CommandDeselect(this)).build(), "deselect")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Name Command")).permission("npc.edit.name").arguments(GenericArguments.remainingJoinedStrings(Text.of("name"))).executor(new CommandName(this)).build(), "name")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Look Command")).permission("npc.edit.look").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("value")))).executor(new CommandLook(this)).build(), "look")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Interact Command")).permission("npc.edit.interact").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("value")))).executor(new CommandInteract(this)).build(), "interact")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Glow Command")).permission("npc.edit.glow").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("value")))).executor(new CommandGlow(this)).build(), "glow")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Helmet Command")).permission("npc.edit.helmet").executor(new CommandArmor(this, EquipmentTypes.HEADWEAR)).build(), "helmet")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Chestplate Command")).permission("npc.edit.chestplate").executor(new CommandArmor(this, EquipmentTypes.CHESTPLATE)).build(), "chestplate")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Leggings Command")).permission("npc.edit.leggings").executor(new CommandArmor(this, EquipmentTypes.LEGGINGS)).build(), "leggings")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Boots Command")).permission("npc.edit.boots").executor(new CommandArmor(this, EquipmentTypes.BOOTS)).build(), "boots")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Hand Command")).permission("npc.edit.hand").executor(new CommandHand(this, HandTypes.MAIN_HAND)).build(), "hand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | OffHand Command")).permission("npc.edit.offhand").executor(new CommandHand(this, HandTypes.OFF_HAND)).build(), "offhand")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Skin Command")).permission("npc.edit.skin").arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSkin(this)).build(), "skin")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Career Command")).permission("npc.edit.career").arguments(GenericArguments.choices(Text.of("type"), choices.getCareers())).executor(new CommandCareer(this)).build(), "career")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Cat Command")).permission("npc.edit.cat").arguments(GenericArguments.choices(Text.of("type"), choices.getCats())).executor(new CommandCat(this)).build(), "cat")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Llama Command")).permission("npc.edit.llama").arguments(GenericArguments.choices(Text.of("type"), choices.getLlamas())).executor(new CommandLlama(this)).build(), "llama")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Size Command")).permission("npc.edit.size").arguments(GenericArguments.integer(Text.of("size"))).executor(new CommandSize(this)).build(), "size")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Sit Command")).permission("npc.edit.sit").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("value")))).executor(new CommandSit(this)).build(), "sit")
				.child(CommandSpec.builder().description(Text.of(TextColors.GOLD, "NPCs | Charged Command")).permission("npc.edit.charged").arguments(GenericArguments.optional(GenericArguments.bool(Text.of("value")))).executor(new CommandCharged(this)).build(), "charged")
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
}