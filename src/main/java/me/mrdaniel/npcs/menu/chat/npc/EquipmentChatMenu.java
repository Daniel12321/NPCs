package me.mrdaniel.npcs.menu.chat.npc;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.interfaces.mixin.NPCAble;
import me.mrdaniel.npcs.utils.TextUtils;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class EquipmentChatMenu extends NPCChatMenu {

    public EquipmentChatMenu(NPCAble npc) {
        super(npc);
    }

    @Override
    public Text getTitle() {
        return Text.of(TextColors.YELLOW, "[ ", TextColors.RED, "NPC Equipment", TextColors.YELLOW, " ]");
    }

    @Nullable
    @Override
    public Text getHeader() {
        return null;
    }

    @Override
    public List<Text> getContents() {
        ArmorEquipable ae = (ArmorEquipable) npc;
        List<Text> lines = Lists.newArrayList();

        lines.add(getArmorText("Helmet", ae.getHelmet()));
        lines.add(getArmorText("Chestplate", ae.getChestplate()));
        lines.add(getArmorText("Leggings", ae.getLeggings()));
        lines.add(getArmorText("Boots", ae.getBoots()));
        lines.add(Text.EMPTY);
        lines.add(getArmorText("MainHand", ae.getItemInHand(HandTypes.MAIN_HAND)));
        lines.add(getArmorText("OffHand", ae.getItemInHand(HandTypes.OFF_HAND)));

        return lines;
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

    public Text getEquipmentButton() {
        return this.getButton(TextUtils.getButton("Equipment", this::send));
    }
}
