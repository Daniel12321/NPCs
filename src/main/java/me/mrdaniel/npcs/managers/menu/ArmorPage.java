package me.mrdaniel.npcs.managers.menu;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.npcs.interfaces.mixin.NPCAble;

public class ArmorPage extends Page {

	public ArmorPage(@Nonnull final NPCAble npc) {
		super(npc);
	}

	@Override
	public void updatePage(final NPCAble npc) {
		int c = 0;

		ArmorEquipable ae = (ArmorEquipable) npc;
		lines[++c] = getArmorText("Helmet", ae.getHelmet());
		lines[++c] = getArmorText("Chestplate", ae.getChestplate());
		lines[++c] = getArmorText("Leggings", ae.getLeggings());
		lines[++c] = getArmorText("Boots", ae.getBoots());
		++c;

		lines[++c] = getArmorText("MainHand", ae.getItemInHand(HandTypes.MAIN_HAND));
		lines[++c] = getArmorText("OffHand", ae.getItemInHand(HandTypes.OFF_HAND));
	}

	private Text getArmorText(@Nonnull final String name, @Nonnull final Optional<ItemStack> item) {
		Text.Builder b = Text.builder()
				.append(Text.of(TextColors.GOLD, name, ": "))
				.append(Text.of(item.isPresent() ? TextColors.DARK_GREEN : TextColors.RED, item.isPresent() ? "True  " : "False  "));
		if (item.isPresent()) {
			b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Remove]")).onHover(TextActions.showText(Text.of(TextColors.RED, "Remove"))).onClick(TextActions.runCommand("/npc " + name.toLowerCase() + " remove")).append(Text.of("  ")).build());
		}
		b.append(Text.builder().append(Text.of(TextColors.YELLOW, "[Give]")).onHover(TextActions.showText(Text.of(TextColors.YELLOW, "Give"))).onClick(TextActions.runCommand("/npc " + name.toLowerCase() + " give")).build());
		return b.build();
	}
}