package me.mrdaniel.npcs.menu.chatmenu;

import com.google.common.collect.Lists;
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

    public PropertiesChatMenu(NPCAble npc) {
        super(npc);
    }

    @Override
    protected Text getTitle() {
        return Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Properties", TextColors.YELLOW, " ]");
    }

    @Override
    protected Text getHeader() {
        return BUTTONS;
    }

    @Override
    protected List<Text> getContents() {
        Position pos = npc.getNPCPosition();
        List<Text> lines = Lists.newArrayList(Text.EMPTY);

        lines.add(Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, npc.getNPCId()));
        lines.add(Text.of(TextColors.GOLD, "Type: ", TextColors.RED, npc.getNPCProperty(PropertyTypes.TYPE).orElse(NPCTypes.HUMAN).getName()));
        lines.add(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, pos.getWorldName(), " ", (int)pos.getX(), " ", (int)pos.getY(), " ", (int)pos.getZ()));
        lines.add(Text.EMPTY);
        if (PropertyTypes.SKIN_UUID.isSupported(npc)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, npc.getNPCProperty(PropertyTypes.SKIN).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build()); }
        lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(npc.getNPCProperty(PropertyTypes.NAME).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build());
        if (PropertyTypes.NAME_VISIBLE.isSupported(npc)) { lines.add(TextUtils.getToggleText("Name Always Visible", "/npc namevisible", npc.getNPCProperty(PropertyTypes.NAME_VISIBLE).orElse(false))); }
        lines.add(Text.EMPTY);
        lines.add(TextUtils.getToggleText("Looking", "/npc looking", npc.getNPCProperty(PropertyTypes.LOOKING).orElse(false)));
        lines.add(TextUtils.getToggleText("Silent", "/npc silent", npc.getNPCProperty(PropertyTypes.SILENT).orElse(false)));
        lines.add(TextUtils.getToggleText("Interact", "/npc interact", npc.getNPCProperty(PropertyTypes.INTERACT).orElse(true)));
        lines.add(TextUtils.getToggleText("Glowing", "/npc glowing", npc.getNPCProperty(PropertyTypes.GLOWING).orElse(false)));
        lines.add(TextUtils.getOptionsText("GlowColor", "/npc glowcolor <color>", npc.getNPCProperty(PropertyTypes.GLOWCOLOR).orElse(GlowColors.WHITE).getName()));
        if (PropertyTypes.BABY.isSupported(npc)) { lines.add(TextUtils.getToggleText("Baby", "/npc baby", npc.getNPCProperty(PropertyTypes.BABY).orElse(false))); }
        if (PropertyTypes.CHARGED.isSupported(npc)) { lines.add(TextUtils.getToggleText("Charged", "/npc charged", npc.getNPCProperty(PropertyTypes.CHARGED).orElse(false))); }
        if (PropertyTypes.ANGRY.isSupported(npc)) { lines.add(TextUtils.getToggleText("Angry", "/npc angry", npc.getNPCProperty(PropertyTypes.ANGRY).orElse(false))); }
        if (PropertyTypes.SIZE.isSupported(npc)) { lines.add(TextUtils.getOptionsText("Size", "/npc size <size>", Integer.toString(npc.getNPCProperty(PropertyTypes.SIZE).orElse(1)))); }
        if (PropertyTypes.SITTING.isSupported(npc)) { lines.add(TextUtils.getToggleText("Sitting", "/npc sitting", npc.getNPCProperty(PropertyTypes.SITTING).orElse(false))); }
        if (PropertyTypes.SADDLE.isSupported(npc)) { lines.add(TextUtils.getToggleText("Saddle", "/npc saddle", npc.getNPCProperty(PropertyTypes.SADDLE).orElse(false))); }
        if (PropertyTypes.HANGING.isSupported(npc)) { lines.add(TextUtils.getToggleText("Hanging", "/npc hanging", npc.getNPCProperty(PropertyTypes.HANGING).orElse(true))); }
        if (PropertyTypes.PUMPKIN.isSupported(npc)) { lines.add(TextUtils.getToggleText("Pumpkin", "/npc pumpkin", npc.getNPCProperty(PropertyTypes.PUMPKIN).orElse(true))); }
        if (PropertyTypes.CAREER.isSupported(npc)) { lines.add(TextUtils.getOptionsText("Career", "/npc career <career>", npc.getNPCProperty(PropertyTypes.CAREER).orElse(Careers.ARMORER).getName())); }
        if (PropertyTypes.HORSEPATTERN.isSupported(npc)) { lines.add(TextUtils.getOptionsText("HorsePattern", "/npc horsepattern <pattern>", npc.getNPCProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getName())); }
        if (PropertyTypes.HORSECOLOR.isSupported(npc)) { lines.add(TextUtils.getOptionsText("HorseColor", "/npc horsecolor <color>", npc.getNPCProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.BROWN).getName())); }
        if (PropertyTypes.LLAMATYPE.isSupported(npc)) { lines.add(TextUtils.getOptionsText("LlamaType", "/npc llamatype <type>", npc.getNPCProperty(PropertyTypes.LLAMATYPE).orElse(LlamaTypes.WHITE).getName())); }
        if (PropertyTypes.CATTYPE.isSupported(npc)) { lines.add(TextUtils.getOptionsText("CatType", "/npc cattype <type>", npc.getNPCProperty(PropertyTypes.CATTYPE).orElse(CatTypes.WILD).getName())); }
        if (PropertyTypes.RABBITTYPE.isSupported(npc)) { lines.add(TextUtils.getOptionsText("RabbitType", "/npc rabbittype <type>", npc.getNPCProperty(PropertyTypes.RABBITTYPE).orElse(RabbitTypes.BROWN).getName())); }
        if (PropertyTypes.PARROTTYPE.isSupported(npc)) { lines.add(TextUtils.getOptionsText("ParrotType", "/npc parrottype <type>", npc.getNPCProperty(PropertyTypes.PARROTTYPE).orElse(ParrotTypes.RED).getName())); }

        return lines;
    }

    public Text getPropertiesButton() {
        return this.getButton(TextUtils.getButton("Properties", this::send));
    }
}
