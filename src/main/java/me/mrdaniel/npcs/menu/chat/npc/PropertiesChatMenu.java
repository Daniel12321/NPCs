package me.mrdaniel.npcs.menu.chat.npc;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.catalogtypes.career.Careers;
import me.mrdaniel.npcs.catalogtypes.cattype.CatTypes;
import me.mrdaniel.npcs.catalogtypes.color.ColorTypes;
import me.mrdaniel.npcs.catalogtypes.horsecolor.HorseColors;
import me.mrdaniel.npcs.catalogtypes.horsepattern.HorsePatterns;
import me.mrdaniel.npcs.catalogtypes.llamatype.LlamaTypes;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCType;
import me.mrdaniel.npcs.catalogtypes.npctype.NPCTypes;
import me.mrdaniel.npcs.catalogtypes.parrottype.ParrotTypes;
import me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes;
import me.mrdaniel.npcs.catalogtypes.rabbittype.RabbitTypes;
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;

public class PropertiesChatMenu extends NPCChatMenu {

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

    public PropertiesChatMenu(INPCData data) {
        super(data);
    }

    @Override
    public Text getTitle() {
        return Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Properties", TextColors.YELLOW, " ]");
    }

    @Override
    public Text getHeader() {
        return BUTTONS;
    }

    @Override
    public List<Text> getContents() {
        Position pos = data.getNPCPosition();
        NPCType type = data.getNPCProperty(PropertyTypes.TYPE).get();
        List<Text> lines = Lists.newArrayList();

        lines.add(Text.EMPTY);
        lines.add(Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, data.getNPCId()));
        lines.add(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, pos.getWorldName(), " ", (int)pos.getX(), " ", (int)pos.getY(), " ", (int)pos.getZ()));
        lines.add(TextUtils.getOptionsText("Type", "/npc type <type>", data.getNPCProperty(PropertyTypes.TYPE).orElse(NPCTypes.HUMAN).getName()));
        lines.add(Text.EMPTY);
        if (PropertyTypes.SKIN_UUID.isSupported(type)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, data.getNPCProperty(PropertyTypes.SKIN).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build()); }
        lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(data.getNPCProperty(PropertyTypes.NAME).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build());
        if (PropertyTypes.NAME_VISIBLE.isSupported(type)) { lines.add(TextUtils.getToggleText("Name Always Visible", "/npc namevisible", data.getNPCProperty(PropertyTypes.NAME_VISIBLE).orElse(false))); }
        lines.add(Text.EMPTY);
        lines.add(TextUtils.getToggleText("Looking", "/npc looking", data.getNPCProperty(PropertyTypes.LOOKING).orElse(false)));
        lines.add(TextUtils.getToggleText("Silent", "/npc silent", data.getNPCProperty(PropertyTypes.SILENT).orElse(false)));
        lines.add(TextUtils.getToggleText("Interact", "/npc interact", data.getNPCProperty(PropertyTypes.INTERACT).orElse(true)));
        lines.add(TextUtils.getToggleText("Glowing", "/npc glowing", data.getNPCProperty(PropertyTypes.GLOWING).orElse(false)));
        lines.add(TextUtils.getOptionsText("GlowColor", "/npc glowcolor <color>", data.getNPCProperty(PropertyTypes.GLOWCOLOR).orElse(ColorTypes.WHITE).getName()));
        if (PropertyTypes.BABY.isSupported(type)) { lines.add(TextUtils.getToggleText("Baby", "/npc baby", data.getNPCProperty(PropertyTypes.BABY).orElse(false))); }
        if (PropertyTypes.CHARGED.isSupported(type)) { lines.add(TextUtils.getToggleText("Charged", "/npc charged", data.getNPCProperty(PropertyTypes.CHARGED).orElse(false))); }
        if (PropertyTypes.ANGRY.isSupported(type)) { lines.add(TextUtils.getToggleText("Angry", "/npc angry", data.getNPCProperty(PropertyTypes.ANGRY).orElse(false))); }
        if (PropertyTypes.SIZE.isSupported(type)) { lines.add(TextUtils.getOptionsText("Size", "/npc size <size>", Integer.toString(data.getNPCProperty(PropertyTypes.SIZE).orElse(1)))); }
        if (PropertyTypes.SITTING.isSupported(type)) { lines.add(TextUtils.getToggleText("Sitting", "/npc sitting", data.getNPCProperty(PropertyTypes.SITTING).orElse(false))); }
        if (PropertyTypes.SADDLE.isSupported(type)) { lines.add(TextUtils.getToggleText("Saddle", "/npc saddle", data.getNPCProperty(PropertyTypes.SADDLE).orElse(false))); }
        if (PropertyTypes.HANGING.isSupported(type)) { lines.add(TextUtils.getToggleText("Hanging", "/npc hanging", data.getNPCProperty(PropertyTypes.HANGING).orElse(true))); }
        if (PropertyTypes.PUMPKIN.isSupported(type)) { lines.add(TextUtils.getToggleText("Pumpkin", "/npc pumpkin", data.getNPCProperty(PropertyTypes.PUMPKIN).orElse(true))); }
        if (PropertyTypes.CAREER.isSupported(type)) { lines.add(TextUtils.getOptionsText("Career", "/npc career <career>", data.getNPCProperty(PropertyTypes.CAREER).orElse(Careers.SHEPHERD).getName())); }
        if (PropertyTypes.HORSEPATTERN.isSupported(type)) { lines.add(TextUtils.getOptionsText("HorsePattern", "/npc horsepattern <pattern>", data.getNPCProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getName())); }
        if (PropertyTypes.HORSECOLOR.isSupported(type)) { lines.add(TextUtils.getOptionsText("HorseColor", "/npc horsecolor <color>", data.getNPCProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.WHITE).getName())); }
        if (PropertyTypes.LLAMATYPE.isSupported(type)) { lines.add(TextUtils.getOptionsText("LlamaType", "/npc llamatype <type>", data.getNPCProperty(PropertyTypes.LLAMATYPE).orElse(LlamaTypes.WHITE).getName())); }
        if (PropertyTypes.CATTYPE.isSupported(type)) { lines.add(TextUtils.getOptionsText("CatType", "/npc cattype <type>", data.getNPCProperty(PropertyTypes.CATTYPE).orElse(CatTypes.WILD).getName())); }
        if (PropertyTypes.RABBITTYPE.isSupported(type)) { lines.add(TextUtils.getOptionsText("RabbitType", "/npc rabbittype <type>", data.getNPCProperty(PropertyTypes.RABBITTYPE).orElse(RabbitTypes.BROWN).getName())); }
        if (PropertyTypes.PARROTTYPE.isSupported(type)) { lines.add(TextUtils.getOptionsText("ParrotType", "/npc parrottype <type>", data.getNPCProperty(PropertyTypes.PARROTTYPE).orElse(ParrotTypes.RED).getName())); }

        return lines;
    }

    public Text getPropertiesButton() {
        return this.getButton(TextUtils.getButton("Properties", this::send));
    }
}
