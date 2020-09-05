package me.mrdaniel.npcs.gui.chat.npc;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.career.Careers;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypes;
import me.mrdaniel.npcs.catalogtypes.color.ColorTypes;
import me.mrdaniel.npcs.catalogtypes.dyecolor.DyeColorTypes;
import me.mrdaniel.npcs.catalogtypes.horsearmor.HorseArmorTypes;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaTypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypes;
import me.mrdaniel.npcs.gui.chat.MenuPage;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

import static me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes.*;
import static me.mrdaniel.npcs.utils.TextUtils.getOptionsText;
import static me.mrdaniel.npcs.utils.TextUtils.getToggleText;

public class PropertiesPage implements MenuPage {

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

    private INPCData data;

    public PropertiesPage(INPCData data) {
        this.data = data;
    }

    @Override
    public boolean shouldShow() {
        return true;
    }

    @Override
    public List<Text> getContents() {
        Position pos = data.getProperty(PropertyTypes.POSITION).get();
        NPCType type = data.getProperty(TYPE).get();
        List<Text> lines = Lists.newArrayList();

        lines.add(Text.of(" "));
        lines.add(Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, data.getId()));
        lines.add(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, pos.getWorldName(), " ", (int)pos.getX(), " ", (int)pos.getY(), " ", (int)pos.getZ()));
        lines.add(getOptionsText("Type", "/npc type <type>", data.getProperty(TYPE).orElse(NPCTypes.HUMAN).getName()));
        lines.add(Text.of(" "));
        if (SKIN_UUID.isSupported(type)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, data.getProperty(SKIN).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build()); }
        lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(data.getProperty(NAME).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build());
        if (NAME_VISIBLE.isSupported(type)) { lines.add(getToggleText("Name Always Visible", "/npc namevisible", data.getProperty(NAME_VISIBLE).orElse(false))); }
        lines.add(Text.of(" "));
        lines.add(getToggleText("Silent", "/npc silent", data.getProperty(SILENT).orElse(false)));
        lines.add(getToggleText("Interact", "/npc interact", data.getProperty(INTERACT).orElse(true)));
        lines.add(getToggleText("Burning", "/npc burning", data.getProperty(BURNING).orElse(false)));
        lines.add(getToggleText("Glowing", "/npc glowing", data.getProperty(GLOWING).orElse(false)));
        lines.add(getOptionsText("GlowColor", "/npc glowcolor <color>", data.getProperty(GLOWCOLOR).orElse(ColorTypes.WHITE).getName()));

        if (ANGRY.isSupported(type)) { lines.add(getToggleText("Angry", "/npc angry", data.getProperty(ANGRY).orElse(false))); }
        if (ARMORED.isSupported(type)) { lines.add(getToggleText("Armored", "/npc armored", data.getProperty(ARMORED).orElse(false))); }
        if (BABY.isSupported(type)) { lines.add(getToggleText("Baby", "/npc baby", data.getProperty(BABY).orElse(false))); }
        if (CHARGED.isSupported(type)) { lines.add(getToggleText("Charged", "/npc charged", data.getProperty(CHARGED).orElse(false))); }
        if (CHEST.isSupported(type)) { lines.add(getToggleText("Chest", "/npc chest", data.getProperty(CHEST).orElse(false))); }
        if (EATING.isSupported(type)) { lines.add(getToggleText("Eating", "/npc eating", data.getProperty(EATING).orElse(false))); }
        if (HANGING.isSupported(type)) { lines.add(getToggleText("Hanging", "/npc hanging", data.getProperty(HANGING).orElse(true))); }
        if (PEEKING.isSupported(type)) { lines.add(getToggleText("Peeking", "/npc peeking", data.getProperty(PEEKING).orElse(false))); }
        if (PUMPKIN.isSupported(type)) { lines.add(getToggleText("Pumpkin", "/npc pumpkin", data.getProperty(PUMPKIN).orElse(true))); }
        if (SCREAMING.isSupported(type)) { lines.add(getToggleText("Screaming", "/npc screaming", data.getProperty(SCREAMING).orElse(false))); }
        if (SHEARED.isSupported(type)) { lines.add(getToggleText("Sheared", "/npc sheared", data.getProperty(SHEARED).orElse(false))); }
        if (SITTING.isSupported(type)) { lines.add(getToggleText("Sitting", "/npc sitting", data.getProperty(SITTING).orElse(false))); }
        if (SADDLE.isSupported(type)) { lines.add(getToggleText("Saddle", "/npc saddle", data.getProperty(SADDLE).orElse(false))); }
        if (SIZE.isSupported(type)) { lines.add(getOptionsText("Size", "/npc size <size>", Integer.toString(data.getProperty(SIZE).orElse(1)))); }
        if (CARRIES.isSupported(type)) { lines.add(getOptionsText("Carries", "/npc carries <blocktype>", data.getProperty(CARRIES).orElse(BlockTypes.AIR).getName())); }
        if (CAREER.isSupported(type)) { lines.add(getOptionsText("Career", "/npc career <career>", data.getProperty(CAREER).orElse(Careers.SHEPHERD).getName())); }
        if (COLOR.isSupported(type)) { lines.add(getOptionsText("Color", "/npc color <color>", data.getProperty(COLOR).orElse(DyeColorTypes.WHITE).getName())); }
        if (HORSEARMOR.isSupported(type)) { lines.add(getOptionsText("HorseArmor", "/npc horsearmor <armor>", data.getProperty(HORSEARMOR).orElse(HorseArmorTypes.NONE).getName())); }
        if (HORSEPATTERN.isSupported(type)) { lines.add(getOptionsText("HorsePattern", "/npc horsepattern <pattern>", data.getProperty(HORSEPATTERN).orElse(HorsePatterns.NONE).getName())); }
        if (HORSECOLOR.isSupported(type)) { lines.add(getOptionsText("HorseColor", "/npc horsecolor <color>", data.getProperty(HORSECOLOR).orElse(HorseColors.WHITE).getName())); }
        if (LLAMATYPE.isSupported(type)) { lines.add(getOptionsText("LlamaType", "/npc llamatype <type>", data.getProperty(LLAMATYPE).orElse(LlamaTypes.WHITE).getName())); }
        if (CATTYPE.isSupported(type)) { lines.add(getOptionsText("CatType", "/npc cattype <type>", data.getProperty(CATTYPE).orElse(CatTypes.WILD).getName())); }
        if (RABBITTYPE.isSupported(type)) { lines.add(getOptionsText("RabbitType", "/npc rabbittype <type>", data.getProperty(RABBITTYPE).orElse(RabbitTypes.BROWN).getName())); }
        if (PARROTTYPE.isSupported(type)) { lines.add(getOptionsText("ParrotType", "/npc parrottype <type>", data.getProperty(PARROTTYPE).orElse(ParrotTypes.RED).getName())); }

        return lines;
    }
}
