package me.mrdaniel.npcs.manager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import me.mrdaniel.npcs.NPCObject;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.action.NPCIterateActions;
import me.mrdaniel.npcs.data.action.NPCRandomActions;
import me.mrdaniel.npcs.data.npc.NPCData;
import me.mrdaniel.npcs.event.NPCEvent;
import me.mrdaniel.npcs.exception.NPCException;
import me.mrdaniel.npcs.io.Config;
import me.mrdaniel.npcs.utils.ServerUtils;

public class NPCManager extends NPCObject {

	private final PaginationService service;
	private final Map<UUID, Living> selected;

	public NPCManager(@Nonnull final NPCs npcs, @Nonnull final Config config) {
		super(npcs);

		this.service = npcs.getGame().getServiceManager().provide(PaginationService.class).get();
		this.selected = Maps.newHashMap();

		Task.builder().delayTicks(200).intervalTicks(config.getNode("npc_update_ticks").getInt(1)).execute(() -> super.getServer().getWorlds().forEach(w -> w.getEntities().forEach(ent -> ent.get(NPCData.class).ifPresent(data -> data.tick((Living)ent))))).submit(super.getNPCs());
	}

	public void spawn(@Nonnull final Player p, @Nonnull final EntityType type) {
		Living npc = (Living) p.getWorld().createEntity(type, p.getLocation().getPosition());
		npc.setRotation(p.getRotation());
		npc.setHeadRotation(p.getHeadRotation());
		npc.offer(Keys.AI_ENABLED, false);
		if (npc instanceof Human) { npc.offer(Keys.CUSTOM_NAME_VISIBLE, true); npc.offer(Keys.DISPLAY_NAME, Text.of("Steve")); }
		npc.offer(new NPCData());

		p.getWorld().spawnEntity(npc, ServerUtils.getSpawnCause(npc));

		try { this.select(p, npc); this.sendMenu(p, npc); }
		catch (final NPCException exc) { p.sendMessage(Text.of(TextColors.RED, exc.getMessage())); }
	}

	@Nonnull
	public Optional<Living> getSelected(@Nonnull final UUID uuid) {
		return Optional.ofNullable(this.selected.get(uuid));
	}

	public void select(@Nonnull final Player p, @Nonnull final Living npc) throws NPCException {
		if (super.getGame().getEventManager().post(new NPCEvent.Select(super.getContainer(), p, npc))) { throw new NPCException("Could not select NPC: Event was cancelled!"); }
		this.selected.put(p.getUniqueId(), npc);
	}

	public void deselect(@Nonnull final UUID uuid) {
		this.selected.remove(uuid);
	}

	public void deselect(@Nonnull final Living npc) {
		this.selected.remove(npc);
	}

	@Nonnull
	public void sendMenu(@Nonnull final Player p, @Nonnull final Living npc) {
		p.sendMessage(Text.EMPTY);

		this.service.builder()
		.title(Text.of("[ ", TextColors.RED, "NPC Menu", TextColors.RESET, " ]"))
		.padding(Text.of("-"))
		.linesPerPage(20)
		.contents(this.getLines(npc))
		.build()
		.sendTo(p);
	}

	@Nonnull
	private List<Text> getLines(@Nonnull final Living npc) {
		NPCData data = npc.get(NPCData.class).get();

		List<Text> lines = Lists.newArrayList();

		// Buttons
		lines.add(Text.builder()
				.append(Text.builder().append(Text.of(TextColors.YELLOW, "   [Move]  ")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Move"))).onClick(TextActions.runCommand("/npc move")).build())
				.append(Text.builder().append(Text.of(TextColors.YELLOW, "   [Deselect]  ")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Deselect"))).onClick(TextActions.runCommand("/npc deselect")).build())
				.append(Text.builder().append(Text.of(TextColors.DARK_GREEN, "  [Mount]  ")).onHover(TextActions.showText(Text.of(TextColors.DARK_GREEN, "Mount"))).onClick(TextActions.runCommand("/npc mount")).build())
				.append(Text.builder().append(Text.of(TextColors.GOLD, "  [Copy]  ")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Copy"))).onClick(TextActions.suggestCommand("/npc copy")).build())
				.append(Text.builder().append(Text.of(TextColors.RED, "  [Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.suggestCommand("/npc remove")).build())
				.build());
		lines.add(Text.of(" "));

		// General Info
		lines.add(Text.of(TextColors.GOLD, "Entity: ", TextColors.RED, capitalize(npc.getType().getName())));
		lines.add(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, npc.getWorld().getName(), " ", npc.getLocation().getBlockX(), " ", npc.getLocation().getBlockY(), " ", npc.getLocation().getBlockZ()));
		lines.add(Text.of("  "));

		// Name and Skin
		lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(npc.get(Keys.DISPLAY_NAME).orElse(Text.of("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build());
		if (npc.supports(Keys.SKIN_UNIQUE_ID)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, data.getSkin())).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build()); }
		lines.add(Text.of("   "));

		// Options
		lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Look: ")).append(getBooleanText(data.isLooking())).onHover(TextActions.showText(getToggleText(data.isLooking()))).onClick(TextActions.runCommand("/npc look " + !data.isLooking())).build());
		lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Interact: ")).append(getBooleanText(data.canInteract())).onHover(TextActions.showText(getToggleText(data.canInteract()))).onClick(TextActions.runCommand("/npc interact " + !data.canInteract())).build());
		lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Glow: ")).append(getBooleanText(npc.get(Keys.GLOWING).orElse(false))).onHover(TextActions.showText(getToggleText(npc.get(Keys.GLOWING).orElse(false)))).onClick(TextActions.runCommand("/npc glow " + !npc.get(Keys.GLOWING).orElse(false))).build());
		if (npc.supports(Keys.CAREER)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Career: ", TextColors.AQUA, npc.get(Keys.CAREER).map(type -> type.getName()).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc career <career>")).build()); }
		if (npc.supports(Keys.OCELOT_TYPE)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Cat: ", TextColors.AQUA, npc.get(Keys.OCELOT_TYPE).map(type -> type.getName()).map(str -> capitalize(str.toLowerCase().replace("cat", "").replace("ocelot", ""))).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc cat <cattype>")).build()); }
		if (npc.supports(Keys.LLAMA_VARIANT)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Llama: ", TextColors.AQUA, npc.get(Keys.LLAMA_VARIANT).map(type -> capitalize(type.getName().toLowerCase())).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc cat <cattype>")).build()); }
		if (npc.supports(Keys.HORSE_STYLE)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Stype: ", TextColors.AQUA, npc.get(Keys.HORSE_STYLE).map(type -> capitalize(type.getName().toLowerCase())).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc style <horsestyle>")).build()); }
		if (npc.supports(Keys.HORSE_COLOR)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Color: ", TextColors.AQUA, npc.get(Keys.HORSE_COLOR).map(type -> capitalize(type.getName().toLowerCase())).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc color <horsecolor>")).build()); }
		if (npc.supports(Keys.SLIME_SIZE)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Size: ", TextColors.AQUA, npc.get(Keys.SLIME_SIZE).orElse(0))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc size <size>")).build()); }
		if (npc.supports(Keys.IS_SITTING)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Sit: ")).append(getBooleanText(npc.get(Keys.IS_SITTING).orElse(false))).onHover(TextActions.showText(getToggleText(npc.get(Keys.IS_SITTING).orElse(false)))).onClick(TextActions.runCommand("/npc sit " + !npc.get(Keys.IS_SITTING).orElse(false))).build()); }
		if (npc.supports(Keys.CREEPER_CHARGED)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Charge: ")).append(getBooleanText(npc.get(Keys.CREEPER_CHARGED).orElse(false))).onHover(TextActions.showText(getToggleText(npc.get(Keys.CREEPER_CHARGED).orElse(false)))).onClick(TextActions.runCommand("/npc charge " + !npc.get(Keys.CREEPER_CHARGED).orElse(false))).build()); }
		lines.add(Text.of("    "));

		// Armor
		if (npc instanceof ArmorEquipable) {
			ArmorEquipable ae = (ArmorEquipable) npc;
			lines.add(getArmorText("Helmet", ae.getHelmet()));
			lines.add(getArmorText("Chestplate", ae.getChestplate()));
			lines.add(getArmorText("Leggings", ae.getLeggings()));
			lines.add(getArmorText("Boots", ae.getBoots()));
			lines.add(getArmorText("Hand", ae.getItemInHand(HandTypes.MAIN_HAND)));
			lines.add(getArmorText("OffHand", ae.getItemInHand(HandTypes.OFF_HAND)));
			lines.add(Text.of("     "));
		}

		// Actions
		lines.add(Text.of(TextColors.GOLD, "Actions: "));
		lines.addAll(data.getActions().getLines());

		lines.add(Text.builder()
				.append(Text.of(TextColors.GOLD, "Add:  "))
				.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Message]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Message"))).onClick(TextActions.suggestCommand("/npc addmessage <message...>")).build())
				.append(Text.of("  "))
				.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Player Command]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Player Command"))).onClick(TextActions.suggestCommand("/npc addplayercmd <command...>")).build())
				.append(Text.of("  "))
				.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Console Command]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Add Console Command"))).onClick(TextActions.suggestCommand("/npc addconsolecmd <command...>")).build())
				.build());

		lines.add(Text.builder()
				.append(Text.of(TextColors.GOLD, "Mode:  "))
				.append(getModeText("Random", "/npc mode random", data.getActions() instanceof NPCRandomActions))
				.append(Text.of("  "))
				.append(getModeText("In Order", "/npc mode inorder", data.getActions() instanceof NPCIterateActions))
				.build());

		return lines;
	}

	@Nonnull
	public static String capitalize(@Nonnull final String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
	}

	@Nonnull
	public static Text getModeText(@Nonnull final String name, @Nonnull final String cmd, final boolean value) {
		if (value) { return Text.builder().append(Text.of(TextColors.DARK_GREEN, "[", name, "]")).build(); }
		else return Text.builder().append(Text.of(TextColors.GRAY, "[", name, "]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Select"))).onClick(TextActions.runCommand(cmd)).build();
	}

	@Nonnull
	public static Text getArmorText(@Nonnull final String name, @Nonnull final Optional<ItemStack> item) {
		Text.Builder b = Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": "))
				.append(Text.of(item.isPresent() ? TextColors.DARK_GREEN : TextColors.RED, item.isPresent() ? "True  " : "False  "));
		if (item.isPresent()) {
			b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Take]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Take"))).onClick(TextActions.runCommand("/npc take" + name.toLowerCase())).append(Text.of("  ")).build());
		}
		b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Give]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Give"))).onClick(TextActions.runCommand("/npc " + name.toLowerCase())).build());
		return b.build();
	}

	@Nonnull
	public static Text getBooleanText(final boolean value) {
		return Text.of(value ? TextColors.DARK_GREEN : TextColors.RED, value ? "Enabled" : "Disabled");
	}

	@Nonnull
	public static Text getToggleText(final boolean value) {
		return Text.of(value ? TextColors.RED : TextColors.DARK_GREEN, value ? "Disable" : "Enable");
	}
}