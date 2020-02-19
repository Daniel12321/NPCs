package me.mrdaniel.npcs.mixin;

import me.mrdaniel.npcs.interfaces.mixin.IMixinEntityVillager;
import net.minecraft.entity.passive.EntityVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EntityVillager.class, priority = 10)
public abstract class MixinEntityVillager implements IMixinEntityVillager {

	@Shadow private int careerId;

	@Override
	public void setCareerId(int careerId) {
		this.careerId = careerId;
	}
}
