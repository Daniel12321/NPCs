package me.mrdaniel.npcs.menu.chat.npc;

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
import me.mrdaniel.npcs.io.INPCData;
import me.mrdaniel.npcs.utils.Position;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Optional;

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

    // TODO: Update
    @Override
    public List<Text> getContents() {
        Position pos = data.getPosition();
        NPCType type = data.getProperty(PropertyTypes.TYPE).get();
        List<Text> lines = Lists.newArrayList();

        lines.add(Text.of(" "));
        lines.add(Text.of(TextColors.GOLD, "NPC ID: ", TextColors.RED, data.getId()));
        lines.add(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, pos.getWorldName(), " ", (int)pos.getX(), " ", (int)pos.getY(), " ", (int)pos.getZ()));
        lines.add(this.getOptionsText("Type", "/npc type <type>", data.getProperty(PropertyTypes.TYPE).orElse(NPCTypes.HUMAN).getName()));
        lines.add(Text.of(" "));
        if (PropertyTypes.SKIN_UUID.isSupported(type)) { lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Skin: ", TextColors.AQUA, data.getProperty(PropertyTypes.SKIN).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc skin <name>")).build()); }
        lines.add(Text.builder().append(Text.of(TextColors.GOLD, "Name: ", TextColors.AQUA)).append(TextUtils.toText(data.getProperty(PropertyTypes.NAME).orElse("None"))).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change"))).onClick(TextActions.suggestCommand("/npc name <name>")).build());
        if (PropertyTypes.NAME_VISIBLE.isSupported(type)) { lines.add(TextUtils.getToggleText("Name Always Visible", "/npc namevisible", data.getProperty(PropertyTypes.NAME_VISIBLE).orElse(false))); }
        lines.add(Text.of(" "));
        lines.add(TextUtils.getToggleText("Looking", "/npc looking", data.getProperty(PropertyTypes.LOOKING).orElse(false)));
        lines.add(TextUtils.getToggleText("Silent", "/npc silent", data.getProperty(PropertyTypes.SILENT).orElse(false)));
        lines.add(TextUtils.getToggleText("Interact", "/npc interact", data.getProperty(PropertyTypes.INTERACT).orElse(true)));
        lines.add(TextUtils.getToggleText("Burning", "/npc burning", data.getProperty(PropertyTypes.BURNING).orElse(false)));
        lines.add(TextUtils.getToggleText("Glowing", "/npc glowing", data.getProperty(PropertyTypes.GLOWING).orElse(false)));
        lines.add(this.getOptionsText("GlowColor", "/npc glowcolor <color>", data.getProperty(PropertyTypes.GLOWCOLOR).orElse(ColorTypes.WHITE).getName()));

        if (PropertyTypes.ANGRY.isSupported(type)) { lines.add(TextUtils.getToggleText("Angry", "/npc angry", data.getProperty(PropertyTypes.ANGRY).orElse(false))); }
        if (PropertyTypes.BABY.isSupported(type)) { lines.add(TextUtils.getToggleText("Baby", "/npc baby", data.getProperty(PropertyTypes.BABY).orElse(false))); }
        if (PropertyTypes.CHARGED.isSupported(type)) { lines.add(TextUtils.getToggleText("Charged", "/npc charged", data.getProperty(PropertyTypes.CHARGED).orElse(false))); }
        if (PropertyTypes.CHEST.isSupported(type)) { lines.add(TextUtils.getToggleText("Chest", "/npc chest", data.getProperty(PropertyTypes.CHEST).orElse(false))); }
        if (PropertyTypes.HANGING.isSupported(type)) { lines.add(TextUtils.getToggleText("Hanging", "/npc hanging", data.getProperty(PropertyTypes.HANGING).orElse(true))); }
        if (PropertyTypes.IGNITED.isSupported(type)) { lines.add(TextUtils.getToggleText("Ignited", "/npc ignited", data.getProperty(PropertyTypes.IGNITED).orElse(false))); }
        if (PropertyTypes.PEEKING.isSupported(type)) { lines.add(TextUtils.getToggleText("Peeking", "/npc peeking", data.getProperty(PropertyTypes.PEEKING).orElse(false))); }
        if (PropertyTypes.PUMPKIN.isSupported(type)) { lines.add(TextUtils.getToggleText("Pumpkin", "/npc pumpkin", data.getProperty(PropertyTypes.PUMPKIN).orElse(true))); }
        if (PropertyTypes.SCREAMING.isSupported(type)) { lines.add(TextUtils.getToggleText("Screaming", "/npc screaming", data.getProperty(PropertyTypes.SCREAMING).orElse(false))); }
        if (PropertyTypes.SHEARED.isSupported(type)) { lines.add(TextUtils.getToggleText("Sheared", "/npc sheared", data.getProperty(PropertyTypes.SHEARED).orElse(false))); }
        if (PropertyTypes.SITTING.isSupported(type)) { lines.add(TextUtils.getToggleText("Sitting", "/npc sitting", data.getProperty(PropertyTypes.SITTING).orElse(false))); }
        if (PropertyTypes.SADDLE.isSupported(type)) { lines.add(TextUtils.getToggleText("Saddle", "/npc saddle", data.getProperty(PropertyTypes.SADDLE).orElse(false))); }
        if (PropertyTypes.WITHER_INVULNERABLE.isSupported(type)) { lines.add(TextUtils.getToggleText("WitherInvulnerable", "/npc witherinvulnerable", data.getProperty(PropertyTypes.WITHER_INVULNERABLE).orElse(false))); }
        if (PropertyTypes.SIZE.isSupported(type)) { lines.add(this.getOptionsText("Size", "/npc size <size>", Integer.toString(data.getProperty(PropertyTypes.SIZE).orElse(1)))); }
        if (PropertyTypes.CAREER.isSupported(type)) { lines.add(this.getOptionsText("Career", "/npc career <career>", data.getProperty(PropertyTypes.CAREER).orElse(Careers.SHEPHERD).getName())); }
        if (PropertyTypes.COLLARCOLOR.isSupported(type)) { lines.add(this.getOptionsText("CollarColor", "/npc collarcolor <color>", data.getProperty(PropertyTypes.COLLARCOLOR).orElse(DyeColorTypes.WHITE).getName())); }
        if (PropertyTypes.WOOLCOLOR.isSupported(type)) { lines.add(this.getOptionsText("WoolColor", "/npc woolcolor <color>", data.getProperty(PropertyTypes.WOOLCOLOR).orElse(DyeColorTypes.WHITE).getName())); }
        if (PropertyTypes.SHULKERCOLOR.isSupported(type)) { lines.add(this.getOptionsText("ShulkerColor", "/npc shulkercolor <color>", data.getProperty(PropertyTypes.SHULKERCOLOR).orElse(DyeColorTypes.PURPLE).getName())); }
        if (PropertyTypes.HORSEARMOR.isSupported(type)) { lines.add(this.getOptionsText("HorseArmor", "/npc horsearmor <armor>", data.getProperty(PropertyTypes.HORSEARMOR).orElse(HorseArmorTypes.NONE).getName())); }
        if (PropertyTypes.HORSEPATTERN.isSupported(type)) { lines.add(this.getOptionsText("HorsePattern", "/npc horsepattern <pattern>", data.getProperty(PropertyTypes.HORSEPATTERN).orElse(HorsePatterns.NONE).getName())); }
        if (PropertyTypes.HORSECOLOR.isSupported(type)) { lines.add(this.getOptionsText("HorseColor", "/npc horsecolor <color>", data.getProperty(PropertyTypes.HORSECOLOR).orElse(HorseColors.WHITE).getName())); }
        if (PropertyTypes.LLAMATYPE.isSupported(type)) { lines.add(this.getOptionsText("LlamaType", "/npc llamatype <type>", data.getProperty(PropertyTypes.LLAMATYPE).orElse(LlamaTypes.WHITE).getName())); }
        if (PropertyTypes.CATTYPE.isSupported(type)) { lines.add(this.getOptionsText("CatType", "/npc cattype <type>", data.getProperty(PropertyTypes.CATTYPE).orElse(CatTypes.WILD).getName())); }
        if (PropertyTypes.RABBITTYPE.isSupported(type)) { lines.add(this.getOptionsText("RabbitType", "/npc rabbittype <type>", data.getProperty(PropertyTypes.RABBITTYPE).orElse(RabbitTypes.BROWN).getName())); }
        if (PropertyTypes.PARROTTYPE.isSupported(type)) { lines.add(this.getOptionsText("ParrotType", "/npc parrottype <type>", data.getProperty(PropertyTypes.PARROTTYPE).orElse(ParrotTypes.RED).getName())); }

        if (type.isArmorEquipable()) {
            lines.add(Text.of(" "));
            lines.add(getArmorText("Helmet", this.data.getProperty(PropertyTypes.HELMET)));
            lines.add(getArmorText("Chestplate", this.data.getProperty(PropertyTypes.CHESTPLATE)));
            lines.add(getArmorText("Leggings", this.data.getProperty(PropertyTypes.LEGGINGS)));
            lines.add(getArmorText("Boots", this.data.getProperty(PropertyTypes.BOOTS)));
            lines.add(Text.of(" "));
            lines.add(getArmorText("MainHand", this.data.getProperty(PropertyTypes.MAINHAND)));
            lines.add(getArmorText("OffHand", this.data.getProperty(PropertyTypes.OFFHAND)));
        }

        return lines;
    }

    public Text getPropertiesButton() {
        return super.getButton(TextUtils.getButton("Properties", this::send));
    }

    private Text getOptionsText(String name, String cmd, String value) {
		return Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": ", TextColors.AQUA, value))
				.onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Change")))
				.onClick(TextActions.suggestCommand(cmd))
				.build();
	}

    private Text getArmorText(String name, Optional<ItemStack> item) {
        boolean hasItem = item.isPresent() && !item.get().isEmpty();

        Text.Builder b = Text.builder()
                .append(Text.of(TextColors.GOLD, name, ": "))
                .append(hasItem ? Text.of(TextColors.DARK_GREEN, "True  ") : Text.of(TextColors.RED, "False  "));
        if (hasItem) {
            b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.runCommand("/npc " + name.toLowerCase() + " remove")).append(Text.of("  ")).build());
        }
        b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Give]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Give"))).onClick(TextActions.runCommand("/npc " + name.toLowerCase() + " give")).build());
        return b.build();
    }
}
