package me.mrdaniel.npcs.gui.inventory;

import me.mrdaniel.npcs.gui.IMenu;
import org.spongepowered.api.item.inventory.InventoryArchetype;

public interface IInventoryMenu extends IMenu {

    void setArchetype(InventoryArchetype archetype);
    void setSize(int x, int y);
}
