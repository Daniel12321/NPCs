package me.mrdaniel.npcs.gui.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.mrdaniel.npcs.NPCs;
import me.mrdaniel.npcs.data.button.ImmutableButtonData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Map;

public abstract class AbstractInventoryMenu implements IInventoryMenu {

    protected final Map<Integer, Button> buttons;

    private Text title;
    private InventoryArchetype archetype;
    private int x;
    private int y;

    protected Player player;
    private Inventory inv;

    public AbstractInventoryMenu() {
        this.buttons = Maps.newHashMap();
    }

    @Override
    public void open() {
        this.inv = Inventory.builder()
                .of(this.archetype)
                .withCarrier(this.player)
                .property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(this.title))
                .property(InventoryDimension.PROPERTY_NAME, InventoryDimension.of(this.x, this.y))
                .listener(InteractInventoryEvent.Close.class, this::onInventoryClose)
                .listener(ClickInventoryEvent.class, this::onInventoryClick)
                .listener(ClickInventoryEvent.Primary.class, e -> this.executeActions(e, ButtonClickType.LEFT))
                .listener(ClickInventoryEvent.Secondary.class, e -> this.executeActions(e, ButtonClickType.RIGHT))
                .build(NPCs.getInstance());
        this.apply();

        Task.builder().delayTicks(0).execute(() -> this.player.openInventory(this.inv)).submit(NPCs.getInstance());
    }

    @Override
    public void update() {
        Task.builder().delayTicks(0).execute(this::apply).submit(NPCs.getInstance());
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setTitle(Text title) {
        this.title = title;
    }

    @Override
    public void setArchetype(InventoryArchetype archetype) {
        this.archetype = archetype;
    }

    @Override
    public void setSize(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected abstract List<Button> getButtons();

    protected void onInventoryClose(InteractInventoryEvent.Close e) {
        this.inv = null;
    }

    protected void onInventoryClick(ClickInventoryEvent e) {
        e.setCancelled(true);
    }

    private void apply() {
        List<Slot> slots = Lists.newArrayList(this.inv.slots());
        slots.forEach(Inventory::clear);
        this.buttons.clear();

        this.getButtons().forEach(b -> {
            b.addButtonData();
            this.buttons.put(b.getIndex(), b);
            slots.get(b.getIndex()).set(b.getItemstack());
        });
    }

    private void executeActions(ClickInventoryEvent e, ButtonClickType click) {
        Player p = e.getCause().first(Player.class).orElse(null);
        if (p == null) {
            return;
        }

        e.getTransactions().forEach(t -> {
            ImmutableButtonData data = t.getOriginal().get(ImmutableButtonData.class).orElse(null);
            if (data != null) {
                Button b = this.buttons.get(data.getIndex());
                if (b != null) {
                    if (click == ButtonClickType.LEFT && b.getLeftAction() != null) {
                        b.getLeftAction().accept(p, t.getSlot());
                    } else if (click == ButtonClickType.RIGHT && b.getRightAction() != null) {
                        b.getRightAction().accept(p, t.getSlot());
                    }
                }
            }
        });
    }
}
