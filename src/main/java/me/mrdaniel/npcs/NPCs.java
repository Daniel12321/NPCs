package me.mrdaniel.npcs;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import me.mrdaniel.npcs.actions.Action;
import me.mrdaniel.npcs.actions.ActionSet;
import me.mrdaniel.npcs.actions.Condition;
import me.mrdaniel.npcs.actions.conditions.ConditionMoney;
import me.mrdaniel.npcs.ai.pattern.AbstractAIPattern;
import me.mrdaniel.npcs.ai.task.AITaskGuardPatrol;
import me.mrdaniel.npcs.ai.task.AITaskGuardRandom;
import me.mrdaniel.npcs.ai.task.AITaskStay;
import me.mrdaniel.npcs.bstats.MetricsLite;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionType;
import me.mrdaniel.npcs.catalogtypes.actiontype.ActionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.aitype.AIType;
import me.mrdaniel.npcs.catalogtypes.aitype.AITypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.career.Career;
import me.mrdaniel.npcs.catalogtypes.career.CareerRegistryModule;
import me.mrdaniel.npcs.catalogtypes.cattype.CatType;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.color.ColorType;
import me.mrdaniel.npcs.catalogtypes.color.ColorTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionType;
import me.mrdaniel.npcs.catalogtypes.conditiontype.ConditionTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorType;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorTypeRegistryModule;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorType;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorTypeRegistryModule;
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
import me.mrdaniel.npcs.data.button.ButtonDataBuilder;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.data.npc.NPCDataBuilder;
import me.mrdaniel.npcs.gui.inventory.IInventoryMenu;
import me.mrdaniel.npcs.io.hocon.config.DefaultConfig;
import me.mrdaniel.npcs.io.hocon.config.MainConfig;
import me.mrdaniel.npcs.io.hocon.typeserializers.*;
import me.mrdaniel.npcs.listeners.EntityListener;
import me.mrdaniel.npcs.listeners.InteractListener;
import me.mrdaniel.npcs.managers.IActionManager;
import me.mrdaniel.npcs.managers.IPlaceholderManager;
import me.mrdaniel.npcs.managers.NPCManager;
import me.mrdaniel.npcs.managers.SelectedManager;
import me.mrdaniel.npcs.managers.actions.ActionManager;
import me.mrdaniel.npcs.managers.actions.DisabledActionManager;
import me.mrdaniel.npcs.utils.LatestVersionSupplier;
import me.mrdaniel.npcs.utils.Position;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
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
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;

import javax.annotation.Nullable;
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

	private final Logger logger;
	@Inject private Game game;
	@Inject private PluginContainer container;
	@Inject @ConfigDir(sharedRoot = false) private Path configDir;

	@Inject private MetricsLite metrics;

	private NPCManager npcManager;
	private SelectedManager selectedManager;
	private IActionManager actionManager;
	private IPlaceholderManager placeholderManager;

	public NPCs() {
		instance = this;
		this.logger = LoggerFactory.getLogger("NPCs");
	}

	@Listener
	public void onPreInit(@Nullable GamePreInitializationEvent e) {
		this.game.getRegistry().registerModule(ActionType.class, new ActionTypeRegistryModule());
		this.game.getRegistry().registerModule(AIType.class, new AITypeRegistryModule());
		this.game.getRegistry().registerModule(Career.class, new CareerRegistryModule());
		this.game.getRegistry().registerModule(CatType.class, new CatTypeRegistryModule());
		this.game.getRegistry().registerModule(ColorType.class, new ColorTypeRegistryModule());
		this.game.getRegistry().registerModule(ConditionType.class, new ConditionTypeRegistryModule());
		this.game.getRegistry().registerModule(DyeColorType.class, new DyeColorTypeRegistryModule());
		this.game.getRegistry().registerModule(HorseArmorType.class, new HorseArmorTypeRegistryModule());
		this.game.getRegistry().registerModule(HorseColor.class, new HorseColorRegistryModule());
		this.game.getRegistry().registerModule(HorsePattern.class, new HorsePatternRegistryModule());
		this.game.getRegistry().registerModule(LlamaType.class, new LlamaTypeRegistryModule());
		this.game.getRegistry().registerModule(NPCType.class, new NPCTypeRegistryModule());
		this.game.getRegistry().registerModule(ParrotType.class, new ParrotTypeRegistryModule());
		this.game.getRegistry().registerModule(PropertyType.class, new PropertyTypeRegistryModule());
		this.game.getRegistry().registerModule(RabbitType.class, new RabbitTypeRegistryModule());

		CatalogTypeSerializer.register(ActionType.class);
		CatalogTypeSerializer.register(AIType.class);
		CatalogTypeSerializer.register(Career.class);
		CatalogTypeSerializer.register(CatType.class);
		CatalogTypeSerializer.register(ColorType.class);
		CatalogTypeSerializer.register(ConditionType.class);
		CatalogTypeSerializer.register(DyeColorType.class);
		CatalogTypeSerializer.register(HorseArmorType.class);
		CatalogTypeSerializer.register(HorseColor.class);
		CatalogTypeSerializer.register(HorsePattern.class);
		CatalogTypeSerializer.register(LlamaType.class);
		CatalogTypeSerializer.register(NPCType.class);
		CatalogTypeSerializer.register(ParrotType.class);
		CatalogTypeSerializer.register(PropertyType.class);
		CatalogTypeSerializer.register(RabbitType.class);
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Action.class), new ActionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Condition.class), new ConditionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(ActionSet.class), new ActionSetTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Position.class), new PositionTypeSerializer());
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(AbstractAIPattern.class), new AIPatternTypeSerializer());

		AITaskStay.register();
		AITaskGuardRandom.register();
		AITaskGuardPatrol.register();

		NPCKeys.init();
		NPCDataBuilder.register();
		ButtonDataBuilder.register();
	}

	@Listener
	public void onInit(@Nullable GameInitializationEvent e) {
		this.logger.info("Loading plugin...");

		MainConfig config = new DefaultConfig<>(MainConfig.class, this.configDir, "config.conf").get();

		this.npcManager = new NPCManager(config, this.configDir);
		this.selectedManager = new SelectedManager(config);
		this.actionManager = config.enable_action_system ? new ActionManager() : new DisabledActionManager();
		this.placeholderManager = IPlaceholderManager.load(config);

		this.game.getEventManager().registerListeners(this, new EntityListener());
		this.game.getEventManager().registerListeners(this, new InteractListener(config));

		this.game.getCommandManager().register(this, new CommandNPC().build(), "npc", "npcs");

		if (config.update_message) {
			Task.builder().async().execute(() -> {
				new LatestVersionSupplier().get().ifPresent(v -> {
					Task.builder().execute(() -> {
						if (!v.equalsIgnoreCase("v" + NPCs.VERSION)) {
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

		// Fixes all loaded NPC entities
		this.game.getServer().getWorlds().forEach(world -> world.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> this.npcManager.onNPCSpawn(ent, data.getId()))));

		// Kicks all players from opened custom inventories
		this.game.getServer().getOnlinePlayers().forEach(p -> p.getOpenInventory()
				.filter(inv -> inv instanceof CarriedInventory)
				.flatMap(inv -> ((CarriedInventory)inv).getCarrier())
				.filter(car -> car instanceof IInventoryMenu)
				.ifPresent(car -> {
					p.closeInventory();
					((IInventoryMenu) car).open();
				})
		);
	}

	@Listener
	public void onServiceChange(ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof EconomyService) {
			ConditionMoney.setEconomyService((EconomyService) e.getNewProvider());
		}
	}

	@Listener(order = Order.LATE)
	public void onQuit(ClientConnectionEvent.Disconnect e) {
		this.selectedManager.deselect(e.getTargetEntity().getUniqueId());
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

	public NPCManager getNPCManager() {
		return npcManager;
	}

	public IActionManager getActionManager() {
		return actionManager;
	}

	public SelectedManager getSelectedManager() {
		return selectedManager;
	}

	public IPlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}
}
