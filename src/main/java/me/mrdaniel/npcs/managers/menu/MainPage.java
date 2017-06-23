package me.mrdaniel.npcs.managers.menu;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.io.NPCFile;
import me.mrdaniel.npcs.utils.TextUtils;

public class MainPage extends Page {

	private static final Text BUTTONS = Text.builder().append(
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.YELLOW, "[Go To]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Teleport to NPC"))).onClick(TextActions.runCommand("/npc goto")).build(),
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.YELLOW, "[Move]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Teleport NPC to you"))).onClick(TextActions.runCommand("/npc move")).build(),
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.YELLOW, "[Deselect]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Deselect"))).onClick(TextActions.runCommand("/npc deselect")).build(),
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.DARK_GREEN, "[Mount]")).onHover(TextActions.showText(Text.of(TextColors.DARK_GREEN, "Mount"))).onClick(TextActions.runCommand("/npc mount")).build(),
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.GOLD, "[Copy]")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Copy"))).onClick(TextActions.suggestCommand("/npc copy")).build(),
			Text.of("   "),
			Text.builder().append(Text.of(TextColors.RED, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.suggestCommand("/npc remove")).build())
			.build();

	public MainPage(@Nonnull final Living npc, @Nonnull final NPCFile file) {
		super(npc, file);
	}

	@Override
	public void updatePage(final Living npc, final NPCFile file) {
		int c = 0;

		lines[c] = BUTTONS;
		++c;

		lines[++c] = Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, file.getId());
		lines[++c] = Text.of(TextColors.GOLD, "Entity: ", TextColors.RED, TextUtils.capitalize(npc.getType().getName()));
		lines[++c] = Text.of(TextColors.GOLD, "Location: ", TextColors.RED, npc.getWorld().getName(), " ", npc.getLocation().getBlockX(), " ", npc.getLocation().getBlockY(), " ", npc.getLocation().getBlockZ());
		++c;

		lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(file.getName().orElse(Text.of("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build();
		if (npc.supports(Keys.SKIN_UNIQUE_ID)) { lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, file.getSkinName().orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build(); }
		++c;

		lines[++c] = TextUtils.getToggleText("Look", "/npc look", file.getLooking());
		lines[++c] = TextUtils.getToggleText("Interact", "/npc interact", file.getInteract());
		if (npc.supports(Keys.CREEPER_CHARGED)) { lines[++c] = TextUtils.getToggleText("Charged", "/npc charged", file.getCharged()); }
		if (npc.supports(Keys.ANGRY)) { lines[++c] = TextUtils.getToggleText("Angry", "/npc angry", file.getAngry()); }
		if (npc.supports(Keys.IS_SITTING)) { lines[++c] = TextUtils.getToggleText("Sit", "/npc sit", file.getSitting()); }
		if (npc.supports(Keys.GLOWING)) { lines[++c] = TextUtils.getToggleText("Glow", "/npc glow", file.getGlow()); }
		if (npc.supports(Keys.GLOWING)) { lines[++c] = TextUtils.getOptionsText("GlowColor", "/npc glowcolor <color>", file.getGlowColor().map(v -> TextUtils.capitalize(v.getName())).orElse("White")); }
		if (npc.supports(Keys.SLIME_SIZE)) { lines[++c] = TextUtils.getOptionsText("Size", "/npc size <size>", String.valueOf(file.getSize())); }
		if (npc.supports(Keys.CAREER)) { lines[++c] = TextUtils.getOptionsText("Career", "/npc career <career>", file.getCareer().map(v -> v.getName()).orElse("None")); }
		if (npc.supports(Keys.OCELOT_TYPE)) { lines[++c] = TextUtils.getOptionsText("Cat", "/npc cat <cattype>", file.getCat().map(v -> TextUtils.capitalize(v.getId().toLowerCase().replace("ocelot", ""))).orElse("None")); }
		if (npc.supports(Keys.HORSE_STYLE)) { lines[++c] = TextUtils.getOptionsText("Style", "/npc style <style>", file.getHorseStyle().map(v -> TextUtils.capitalize(v.getName().toLowerCase())).orElse("None")); }
		if (npc.supports(Keys.HORSE_COLOR)) { lines[++c] = TextUtils.getOptionsText("Color", "/npc color <color>", file.getHorseColor().map(v -> TextUtils.capitalize(v.getName().toLowerCase())).orElse("None")); }
		if (npc.supports(Keys.LLAMA_VARIANT)) { lines[++c] = TextUtils.getOptionsText("Variant", "/npc variant <variant>", file.getVariant().map(v -> TextUtils.capitalize(v.getName().toLowerCase())).orElse("None")); }
	}
}