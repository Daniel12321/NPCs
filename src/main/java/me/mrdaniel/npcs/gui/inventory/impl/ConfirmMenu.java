package me.mrdaniel.npcs.gui.inventory.impl;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.gui.inventory.AbstractInventoryMenu;
import me.mrdaniel.npcs.gui.inventory.Button;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.function.BiConsumer;

public class ConfirmMenu extends AbstractInventoryMenu {

    private final BiConsumer<Player, Slot> confirmAction;
    private final BiConsumer<Player, Slot> returnAction;

    public ConfirmMenu(Player player, BiConsumer<Player, Slot> confirmAction, BiConsumer<Player, Slot> returnAction, Text title) {
        this.confirmAction = confirmAction;
        this.returnAction = returnAction;

        this.setPlayer(player);
        this.setArchetype(InventoryArchetypes.HOPPER);
        this.setTitle(title);
    }

    @Override
    protected List<Button> getButtons() {
        Button confirm = new Button()
                .setItemStack(ItemStack.builder()
                        .itemType(ItemTypes.DYE)
                        .add(Keys.DYE_COLOR, DyeColors.LIME)
                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, "Confirm"))
                        .build())
                .setIndex(1)
                .setLeftAction(this.confirmAction.andThen(this.returnAction));

        Button cancel = new Button()
                .setItemStack(ItemStack.builder()
                        .itemType(ItemTypes.DYE)
                        .add(Keys.DYE_COLOR, DyeColors.RED)
                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.RED, "Cancel"))
                        .build())
                .setIndex(3)
                .setLeftAction(this.returnAction);

        return Lists.newArrayList(confirm, cancel);
    }
}