package me.mrdaniel.npcs.gui.inventory;

import com.google.common.collect.Lists;
import me.mrdaniel.npcs.NPCs;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.scheduler.Task;

import java.util.List;

public class ModifierTask implements Runnable {

    private List<Button> modifiers;

    private int interval;
    private Player player;
    private Inventory inv;
    private Task task;

    public ModifierTask(int interval, Player player, Inventory inv) {
        this.modifiers = Lists.newArrayList();

        this.interval = interval;
        this.player = player;
        this.inv = inv;
        this.task = null;
    }

    public ModifierTask addModifier(Button button) {
        this.modifiers.add(button);
        return this;
    }

    public void start() {
        if (this.task != null) {
            this.task.cancel();
        }

        this.task = Task.builder()
                .delayTicks(this.interval)
                .intervalTicks(this.interval)
                .execute(this)
                .submit(NPCs.getInstance());
    }

    @Override
    public void run() {
        if (!this.player.isOnline() || this.player.isRemoved()) { // TODO: Choose
            this.cancel();
        }

        List<Slot> slots = Lists.newArrayList(this.inv.slots());
        for (Button button : this.modifiers) {
            Slot slot = slots.get(button.getIndex());
            ItemStack item = slot.peek().orElse(null);
            if (item == null) {
                continue;
            }

            button.getModifier().accept(item);
            slot.set(item);
        }
    }

    public void cancel() {
        this.modifiers.clear();
        this.player = null;
        this.inv = null;

        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }
}
