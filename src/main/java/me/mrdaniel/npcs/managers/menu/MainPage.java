package me.mrdaniel.npcs.managers.menu;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.catalogtypes.career.Careers;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypes;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColors;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaTypes;
import me.mrdaniel.npcs.catalogtypes.optiontype.OptionTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypes;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
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

	public MainPage(@Nonnull final NPCAble npc) {
		super(npc);
	}

	@Override
	public void updatePage(final NPCAble npc) {
		NPCFile file = npc.getNPCFile();
		int c = 0;

		lines[c++] = BUTTONS;
		lines[++c] = Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, file.getId());
		lines[++c] = Text.of(TextColors.GOLD, "Type: ", TextColors.RED, TextUtils.capitalize(((Living)npc).getType().getName()));
		lines[++c] = Text.of(TextColors.GOLD, "Location: ", TextColors.RED, file.getWorldName(), " ", (int)file.getX(), " ", (int)file.getY(), " ", (int)file.getZ());
		++c;

		lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(file.getName().orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build();
		if (OptionTypes.SKIN.isSupported(npc)) { lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, file.getSkinName().orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build(); }
		++c;

		lines[++c] = TextUtils.getToggleText("Looking", "/npc looking", file.getLooking());
		lines[++c] = TextUtils.getToggleText("Interact", "/npc interact", file.getInteract());
		lines[++c] = TextUtils.getToggleText("Silent", "/npc silent", file.getSilent());
		if (OptionTypes.GLOWING.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Glowing", "/npc glowing", file.getGlowing()); }
		if (OptionTypes.GLOWCOLOR.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("GlowColor", "/npc glowcolor <color>", file.getGlowColor().orElse(GlowColors.WHITE).getName()); }
		if (OptionTypes.BABY.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Baby", "/npc baby", file.getBaby()); }
		if (OptionTypes.CHARGED.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Charged", "/npc charged", file.getCharged()); }
		if (OptionTypes.ANGRY.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Angry", "/npc angry", file.getAngry()); }
		if (OptionTypes.SIZE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("Size", "/npc size <size>", String.valueOf(file.getSize())); }
		if (OptionTypes.SITTING.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Sitting", "/npc sitting", file.getSitting()); }
		if (OptionTypes.SADDLE.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Saddle", "/npc saddle", file.getSaddle()); }
		if (OptionTypes.HANGING.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Hanging", "/npc hanging", file.getHanging()); }
		if (OptionTypes.PUMPKIN.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Pumpkin", "/npc pumpkin", file.getPumpkin()); }
		if (OptionTypes.CAREER.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("Career", "/npc career <career>", file.getCareer().orElse(Careers.ARMORER).getName()); }
		if (OptionTypes.HORSEPATTERN.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("HorsePattern", "/npc horsepattern <pattern>", file.getHorsePattern().orElse(HorsePatterns.NONE).getName()); }
		if (OptionTypes.HORSECOLOR.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("HorseColor", "/npc horsecolor <color>", file.getHorseColor().orElse(HorseColors.BROWN).getName()); }
		if (OptionTypes.LLAMATYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("LlamaType", "/npc llamatype <type>", file.getLlamaType().orElse(LlamaTypes.WHITE).getName()); }
		if (OptionTypes.CATTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("CatType", "/npc cattype <type>", file.getCatType().orElse(CatTypes.WILD).getName()); }
		if (OptionTypes.RABBITTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("RabbitType", "/npc rabbittype <type>", file.getRabbitType().orElse(RabbitTypes.BROWN).getName()); }
		if (OptionTypes.PARROTTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("ParrotType", "/npc parrottype <type>", file.getParrotType().orElse(ParrotTypes.RED).getName()); }
	}
}