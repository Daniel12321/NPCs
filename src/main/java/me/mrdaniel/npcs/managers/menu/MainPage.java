package me.mrdaniel.npcs.managers.menu;

import me.mrdaniel.npcs.catalogtypes.career.Careers;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypes;
import me.mrdaniel.npcs.catalogtypes.glowcolor.GlowColors;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaTypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypes;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

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

	public MainPage(NPCAble npc) {
		super(npc);
	}

	@Override
	public void updatePage(NPCAble npc) {
		INPCData data = npc.getNPCData();
		Position pos = data.getProperty(PropertyTypes.POSITION).get();
		int c = 0;

		lines[c++] = BUTTONS;
		lines[++c] = Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, data.getId());
		lines[++c] = Text.of(TextColors.GOLD, "Type: ", TextColors.RED, data.getProperty(PropertyTypes.TYPE).orElse(NPCTypes.HUMAN).getName());
		lines[++c] = Text.of(TextColors.GOLD, "Location: ", TextColors.RED, data.getWorldName(), " ", (int)pos.getX(), " ", (int)pos.getY(), " ", (int)pos.getZ());
		++c;

		if (PropertyTypes.SKIN_UUID.isSupported(npc)) { lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, data.getProperty(PropertyTypes.SKIN).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build(); }
		lines[++c] = Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(data.getProperty(PropertyTypes.NAME).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build();
		lines[++c] = TextUtils.getToggleText("Name Always Visible", "/npc namevisible", data.getProperty(PropertyTypes.NAME_VISIBLE).orElse(false));
		++c;

		lines[++c] = TextUtils.getToggleText("Looking", "/npc looking", data.getProperty(PropertyTypes.LOOKING).orElse(false));
		lines[++c] = TextUtils.getToggleText("Silent", "/npc silent", data.getProperty(PropertyTypes.SILENT).orElse(false));
		lines[++c] = TextUtils.getToggleText("Interact", "/npc interact", data.getProperty(PropertyTypes.INTERACT).orElse(true));
		lines[++c] = TextUtils.getToggleText("Glowing", "/npc glowing", data.getProperty(PropertyTypes.GLOWING).orElse(false));
		lines[++c] = TextUtils.getOptionsText("GlowColor", "/npc glowcolor <color>", data.getProperty(PropertyTypes.GLOWCOLOR).orElse(GlowColors.WHITE).getName());
		if (PropertyTypes.BABY.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Baby", "/npc baby", data.getProperty(PropertyTypes.BABY).orElse(false)); }
		if (PropertyTypes.CHARGED.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Charged", "/npc charged", data.getProperty(PropertyTypes.CHARGED).orElse(false)); }
		if (PropertyTypes.ANGRY.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Angry", "/npc angry", data.getProperty(PropertyTypes.ANGRY).orElse(false)); }
		if (PropertyTypes.SIZE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("Size", "/npc size <size>", Integer.toString(data.getProperty(PropertyTypes.SIZE).orElse(1))); }
		if (PropertyTypes.SITTING.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Sitting", "/npc sitting", data.getProperty(PropertyTypes.SITTING).orElse(false)); }
		if (PropertyTypes.SADDLE.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Saddle", "/npc saddle", data.getProperty(PropertyTypes.SADDLE).orElse(false)); }
		if (PropertyTypes.HANGING.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Hanging", "/npc hanging", data.getProperty(PropertyTypes.HANGING).orElse(true)); }
		if (PropertyTypes.PUMPKIN.isSupported(npc)) { lines[++c] = TextUtils.getToggleText("Pumpkin", "/npc pumpkin", data.getProperty(PropertyTypes.PUMPKIN).orElse(true)); }
		if (PropertyTypes.CAREER.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("Career", "/npc career <career>", data.getProperty(PropertyTypes.CAREER).orElse(Careers.ARMORER).getName()); }
		if (PropertyTypes.HORSEPATTERN.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("HorsePattern", "/npc horsepattern <pattern>", data.getProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getName()); }
		if (PropertyTypes.HORSECOLOR.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("HorseColor", "/npc horsecolor <color>", data.getProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.BROWN).getName()); }
		if (PropertyTypes.LLAMATYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("LlamaType", "/npc llamatype <type>", data.getProperty(PropertyTypes.LLAMATYPE).orElse(LlamaTypes.WHITE).getName()); }
		if (PropertyTypes.CATTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("CatType", "/npc cattype <type>", data.getProperty(PropertyTypes.CATTYPE).orElse(CatTypes.WILD).getName()); }
		if (PropertyTypes.RABBITTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("RabbitType", "/npc rabbittype <type>", data.getProperty(PropertyTypes.RABBITTYPE).orElse(RabbitTypes.BROWN).getName()); }
		if (PropertyTypes.PARROTTYPE.isSupported(npc)) { lines[++c] = TextUtils.getOptionsText("ParrotType", "/npc parrottype <type>", data.getProperty(PropertyTypes.PARROTTYPE).orElse(ParrotTypes.RED).getName()); }
	}
}
