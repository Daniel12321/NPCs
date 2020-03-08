package me.mrdaniel.npcs.mixin.mixin;

import me.mrdaniel.npcs.mixin.interfaces.INPCMixin;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 10)
public abstract class MixinEntity implements INPCMixin {

	@Inject(method = "isEntityInvulnerable", at = @At("RETURN"), cancellable = true)
	public void onIsEntityInvulnerable(final DamageSource source, final CallbackInfoReturnable<Boolean> cir) {
		if (this.isNPC() && !cir.getReturnValue()) {
			cir.setReturnValue(true);
		}
	}
}
