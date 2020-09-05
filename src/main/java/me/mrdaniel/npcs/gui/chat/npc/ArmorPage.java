package me.mrdaniel.npcs.gui.chat.npc;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.gui.chat.MenuPage;
import me.mrdaniel.npcs.io.INPCData;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.Optional;

import static me.mrdaniel.npcs.catalogtypes.propertytype.PropertyTypes.*;

public class ArmorPage implements MenuPage {

    private INPCData data;

    public ArmorPage(INPCData data) {
        this.data = data;
    }

    @Override
    public boolean shouldShow() {
        return this.data.getProperty(TYPE).get().isArmorEquipable();
    }

    @Override
    public List<Text> getContents() {
        return Lists.newArrayList(
                Text.of(" "),
                getArmorText("Helmet", this.data.getProperty(HELMET)),
                getArmorText("Chestplate", this.data.getProperty(CHESTPLATE)),
                getArmorText("Leggings", this.data.getProperty(LEGGINGS)),
                getArmorText("Boots", this.data.getProperty(BOOTS)),
                Text.of(" "),
                getArmorText("MainHand", this.data.getProperty(MAINHAND)),
                getArmorText("OffHand", this.data.getProperty(OFFHAND))
        );
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
