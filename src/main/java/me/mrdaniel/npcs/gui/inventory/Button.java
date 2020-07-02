package me.mrdaniel.npcs.gui.inventory;

import me.mrdaniel.npcs.data.button.ButtonData;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.text.Text;

import java.util.function.BiConsumer;

public class Button {

    private int index;
    private ItemStack itemstack;
    private BiConsumer<Player, Slot> leftAction;
    private BiConsumer<Player, Slot> rightAction;

    public Button() {
        this.index = -1;
        this.itemstack = null;
        this.leftAction = null;
        this.rightAction = null;
    }

    public int getIndex() {
        return index;
    }

    public ItemStack getItemstack() {
        return this.itemstack;
    }

    public BiConsumer<Player, Slot> getLeftAction() {
        return this.leftAction;
    }

    public BiConsumer<Player, Slot> getRightAction() {
        return this.rightAction;
    }

    public Button index(int index) {
        this.index = index;
        return this;
    }

    public Button itemstack(ItemStack itemstack) {
        this.itemstack = itemstack;
        return this;
    }

    public Button leftAction(BiConsumer<Player, Slot> action) {
        this.leftAction = action;
        return this;
    }

    public Button rightAction(BiConsumer<Player, Slot> action) {
        this.rightAction = action;
        return this;
    }

    public Button addButtonData() {
        this.itemstack.offer(new ButtonData(this.index));
        return this;
    }

    public static Button spacer(int index) {
        return spacer().index(index);
    }

    public static Button spacer() {
        return new Button().itemstack(ItemStack.builder()
                .itemType(ItemTypes.STAINED_GLASS_PANE)
                .quantity(1)
                .add(Keys.DYE_COLOR, DyeColors.BLACK)
                .add(Keys.DISPLAY_NAME, Text.of(" "))
                .build());
    }

//    public static Button back() {
//        return new Button()
//                .itemstack(ItemStack.builder()
//                        .itemType(ItemTypes.ARROW)
//                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.RED, "Back"))
//                        .build());
//    }
//    public static MenuButton add(String title) {
//        return new MenuButton()
//                .itemstack(ItemStack.builder()
//                        .itemType(ItemTypes.DYE)
//                        .add(Keys.DYE_COLOR, DyeColors.LIME)
//                        .add(Keys.DISPLAY_NAME, Text.of(TextColors.GREEN, title))
//                        .build());
//    }
}
