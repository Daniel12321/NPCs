package me.mrdaniel.npcs.ai;

import me.mrdaniel.npcs.utils.Position;
import net.minecraft.entity.EntityLiving;

public interface INPCAITask {

    default void moveTo(EntityLiving el, Position pos, double movementSpeed) {
        if (!el.getNavigator().tryMoveToXYZ(pos.getX(), pos.getY(), pos.getZ(), movementSpeed)) {
            el.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
