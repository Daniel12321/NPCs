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
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotType;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyType;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitType;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypeRegistryModule;
import me.mrdaniel.npcs.commands.main.CommandNPC;
import me.mrdaniel.npcs.data.NPCKeys;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.exceptions.NPCException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.listeners.EntityListener;
import me.mrdaniel.npcs.listeners.InteractListener;
import me.mrdaniel.npcs.managers.ActionManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.PlaceholderManager;
import me.mrdaniel.npcs.managers.SelectedManager;
import me.mrdaniel.npcs.utils.ServerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;

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
	private final SelectedManager menuManager;
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
		this.menuManager = new SelectedManager(this.config);
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

		this.game.getEventManager().registerListeners(this, new EntityListener());
		this.game.getEventManager().registerListeners(this, new InteractListener());

		this.game.getCommandManager().register(this, new CommandNPC().build(), "npc", "npcs");

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

		this.logger.info("Loaded plugin successfully.");
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
					this.logger.error("Failed to respawn NPC " + data.getNPCId() + ": ", exc);
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

	public SelectedManager getMenuManager() {
		return menuManager;
	}

	public PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}
}
